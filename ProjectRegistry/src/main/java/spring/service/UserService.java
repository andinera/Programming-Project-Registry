package spring.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.dao.UserDAO;
import spring.model.Idea;
import spring.proxy.Proxy;
import spring.model.Development;

@Service("userService")
public class UserService {
	
	@Autowired
	private UserDetailsManager userDetailsService;
	
	@Autowired
	private UserDAO dao;
	
	private Map<String, Proxy<spring.model.User>> proxies = new Hashtable<String, Proxy<spring.model.User>>();
	
	
	public void save(String username, String password) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);
		
		UserDetails userDetails = User.builder().username(username)
					.password(password)
					.disabled(false)
					.authorities(result)
					.build();
		userDetailsService.createUser(userDetails);
	}
	
	public void update(spring.model.User user) {
		Hibernate.initialize(user.getUserRoles());
		Hibernate.initialize(user.getIdeas());
		Hibernate.initialize(user.getDevelopments());
		dao.update(user);
	}
	
	public spring.model.User create() {
		return new spring.model.User();
	}
	
	public spring.model.User loadByUsername(String username) {
		spring.model.User user = dao.loadByUsername(username);
		Hibernate.initialize(user.getUserRoles());
		Hibernate.initialize(user.getIdeas());
		for(Idea idea : user.getIdeas()) {
			Hibernate.initialize(idea.getVotes());
		}
		Hibernate.initialize(user.getDevelopments());
		for(Development development : user.getDevelopments()) {
			Hibernate.initialize(development.getVotes());
		}
		return user;
	}
	
	@Transactional
	public void setProxy(HttpSession session, String keyword) {
		List<spring.model.User> users = dao.loadByKeyword(keyword);
		for (spring.model.User user: users) {
			Hibernate.initialize(user.getIdeas());
			for (Idea idea : user.getIdeas()) {
				Hibernate.initialize(idea.getVotes());
			}
			Hibernate.initialize(user.getDevelopments());
			for (Development development : user.getDevelopments()) {
				Hibernate.initialize(development.getVotes());
			}
		}
		users.sort(new UserComparator());
		proxies.put(session.getId(), new Proxy<spring.model.User>());
		proxies.get(session.getId()).setData(users);
		session.setAttribute("numUserPages", proxies.get(session.getId()).getNumPages());
	}
	
	public List<spring.model.User> getProxy(HttpSession session, Integer homePage) {
		List<spring.model.User> users = proxies.get(session.getId()).getDataByPage(homePage);
		session.setAttribute("userPage", proxies.get(session.getId()).getPage());
		return users;
	}
}

class UserComparator implements Comparator<spring.model.User> {
	@Override
	public int compare(spring.model.User left, spring.model.User right) {
		int l = 0;
		for (Idea idea : left.getIdeas()) {
			l += idea.voteCount();
		}
		for (Development development : left.getDevelopments()) {
			l += development.voteCount();
		}
		int r = 0;
		for (Idea idea : right.getIdeas()) {
			r += idea.voteCount();
		}
		for (Development development : right.getDevelopments()) {
			r += development.voteCount();
		}
		return Integer.compare(r, l);
	}
}
