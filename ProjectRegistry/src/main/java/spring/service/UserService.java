package spring.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import spring.builder.UserBuilder;
import spring.dao.UserDAO;
import spring.model.Idea;
import spring.model.Development;

@Component("userService")
@ComponentScan("spring.builder")
public class UserService {
	
	@Autowired
	private UserBuilder userBuilder;
	
	@Autowired
	private UserDetailsManager userDetailsService;
	
	@Autowired
	private UserDAO userDAO;
	
	
	public spring.model.User buildUser() {
		return userBuilder.build();
	}
	
	@Transactional
	public void saveUser(String username, String password) {
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
	public void updateUser(spring.model.User user) {
		Hibernate.initialize(user.getUserRoles());
		Hibernate.initialize(user.getIdeas());
		Hibernate.initialize(user.getDevelopments());
		userDAO.updateUser(user);
	}
	
	@Transactional(readOnly=true)
	public List<spring.model.User> loadUsersBySearch(String search) {
		List<spring.model.User> users = userDAO.loadUsersBySearch(search);
		return users;
	}
	
	@Transactional(readOnly=true)
	public spring.model.User loadUserByUsername(String username) {
		spring.model.User user = userDAO.loadUserByUsername(username);
		Hibernate.initialize(user.getUserRoles());
		Hibernate.initialize(user.getIdeas());
		for(Idea idea : user.getIdeas()) {
			Hibernate.initialize(idea.getVotes());
		}
		Hibernate.initialize(user.getDevelopments());
		for(Development development : user.getDevelopments()) {
			Hibernate.initialize(development.getVotes());
			Hibernate.initialize(development.getIdea());
		}
		return user;
	}
}
