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

import spring.comparator.UserComparatorByVote;
import spring.dao.UserDAO;
import spring.proxy.Proxy;


/**
 * Service for handling all requests related to {@link spring.model.User}s. Maintains a 
 * proxy of {@link spring.model.User}s for each session.
 * 
 * @author Shane Lockwood
 *
 */
@Service
public class UserService {
	
	@Autowired
	private UserDetailsManager userDetailsService;
	
	@Autowired
	@Qualifier("userDAO")
	private UserDAO dao;
	
	private Map<String, Proxy<spring.model.User>> sessionProxies = new Hashtable<String, Proxy<spring.model.User>>();
	
	
	/**
	 * Creates a UserDetails to be converted to a {@link spring.model.User} and saved to a 
	 * database.
	 * 
	 * @param username The String representing the username.
	 * @param password The String representing the password.
	 */
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
	
	/**
	 * Updates an existing {@link spring.model.User} to a database.
	 * 
	 * @param user The {@link spring.model.User} to be updated.
	 */
	public void update(spring.model.User user) {
		dao.update(user);
	}
	
	/**
	 * Saves or updates a {@link spring.model.User} depending on its current state.
	 * 
	 * @param user The {@link spring.model.User} to be saved or updated.
	 */
	public void saveOrUpdate(spring.model.User user) {
		dao.saveOrUpdate(user);
	}
	
	/**
	 * Returns a {@link spring.model.User} from the proxied {@link spring.model.User}s or loads it 
	 * from a database.
	 * 
	 * @param sessionId The String representing the session's id.
	 * @param username The String representing the {@link spring.model.User}'s username.
	 * @return {@link spring.model.User}
	 */
	public spring.model.User loadByUsername(String sessionId, String username) {
		Proxy<spring.model.User> proxy = sessionProxies.get(sessionId);
		if (proxy == null) {
			proxy = new Proxy<spring.model.User>();
			sessionProxies.put(sessionId, proxy);
		} else {
			List<spring.model.User> users = proxy.getPagedData();
			if (users != null) {
				for (spring.model.User user : users) {
					if (user.getUsername().equals(username)) {
						return user;
					}
				}
			}
		}
		return dao.loadById(username);
	}
	
	/**
	 * Loads {@link spring.model.User}s from a database with usernames that contain the passed
	 * keyword. Proxies the returne  {@link spring.model.User}s with the passed page and returns 
	 * the {@link spring.proxy.Proxy}. The proxied data is sorted with 
	 * {@link spring.comparator.UserComparatorByVote}.
	 * 
	 * @param sessionId The String representing the session's id.
	 * @param keyword The String to be used in the database query.
	 * @param page The int representing the subset of data to be stored in the 
	 * {@link spring.proxy.Proxy}.
	 * @return {@link spring.proxy.Proxy}
	 */
	public Proxy<spring.model.User> loadByPage(String sessionId, String keyword, int page) {
		Proxy<spring.model.User> proxy = sessionProxies.get(sessionId);
		if (proxy == null) {
			proxy = new Proxy<spring.model.User>();
			sessionProxies.put(sessionId, proxy);
		}
		TreeSet<spring.model.User> dataSet = new TreeSet<spring.model.User>(new UserComparatorByVote());
		dataSet.addAll(dao.loadByKeyword(keyword));
		proxy.setPagedData(dataSet, page);
		return proxy;
		
	}
}
