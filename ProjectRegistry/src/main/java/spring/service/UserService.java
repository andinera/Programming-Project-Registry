package spring.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import spring.model.Development;

@Service("userService")
public class UserService {
	
	@Autowired
	private UserDetailsManager userDetailsService;
	
	@Autowired
	private UserDAO dao;
	
	
	@Transactional
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
	
	@Transactional
	public void update(spring.model.User user) {
		Hibernate.initialize(user.getUserRoles());
		Hibernate.initialize(user.getIdeas());
		Hibernate.initialize(user.getDevelopments());
		dao.update(user);
	}
	
	public spring.model.User create() {
		return new spring.model.User();
	}
	
	@Transactional(readOnly=true)
	public List<spring.model.User> loadBySearch(String search) {
		List<spring.model.User> users = dao.loadBySearch(search);
		return users;
	}
	
	@Transactional(readOnly=true)
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
}
