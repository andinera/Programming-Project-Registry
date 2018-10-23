package spring.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import spring.model.User;
import spring.model.UserRole;

@Repository
public class UserDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public void createUser(User user) {
		if (loadUserByUsername(user.getUsername()) != null) {
			sessionFactory.getCurrentSession().save(user);
			Set<UserRole> userRoles = user.getUserRoles();
			for (UserRole userRole : userRoles) {
				sessionFactory.getCurrentSession().save(userRole);
			}
		}
	}
	
	public void updateUser(User user) {
		sessionFactory.getCurrentSession().update(user);
	}
	
	@SuppressWarnings("unchecked")
	public User loadUserByUsername(String username) {
		List<User> users = new ArrayList<User>();
		
		users = sessionFactory.getCurrentSession()
				.createQuery("from User where username=:username")
				.setParameter("username",  username)
				.list();
		
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<User> loadUsersBySearch(String search) {
		List<User> users = sessionFactory.getCurrentSession()
					.createQuery("from User where username like :keyword")
					.setParameter("keyword", "%" + search + "%")
					.list();
		return users;
	}
}
