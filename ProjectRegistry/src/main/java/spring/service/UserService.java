package spring.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.Comparator.UserComparatorByVote;
import spring.dao.UserDAO;
import spring.proxy.Proxy;

@Service
public class UserService {
	
	@Autowired
	private UserDetailsManager userDetailsService;
	
	@Autowired
	@Qualifier("userDAO")
	private UserDAO dao;
	
	private Map<String, Proxy<spring.model.User>> sessionProxies = new Hashtable<String, Proxy<spring.model.User>>();
	
	
	public void save(String username, String password) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(setAuths);
		
		UserDetails userDetails = User.builder().username(username)
												.password(password)
												.disabled(false)
												.authorities(authorities)
												.build();
		userDetailsService.createUser(userDetails);
	}
	
	public void update(spring.model.User user) {
		dao.update(user);
	}
	
	public void saveOrUpdate(spring.model.User user) {
		dao.saveOrUpdate(user);
	}
	
	public spring.model.User merge(spring.model.User user) {
		user = (spring.model.User) dao.merge(user);
		return user;
	}
	
	public spring.model.User loadByUsername(String username) {
		return dao.loadById(username);
	}
	
	public Proxy<spring.model.User> loadByPage(String sessionId, String keyword, int page) {
		Proxy<spring.model.User> proxy = sessionProxies.get(sessionId);
		if (proxy == null) {
			proxy = new Proxy<spring.model.User>();
		}
		Set<spring.model.User> dataSet = new TreeSet<spring.model.User>(new UserComparatorByVote());
		dataSet.addAll(dao.loadByKeyword(keyword));
		proxy.setPagedData(dataSet, page);
		return proxy;
		
	}
}
