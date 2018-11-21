package spring.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.dao.UserDAO;
import spring.model.UserRole;


/**
 * Service required for handling user login/logout functionality.
 * 
 * @author Shane Lockwood
 *
 */
@Service("userDetailsService")
public class UserDetailsService implements UserDetailsManager {

	@Autowired
	@Qualifier("userDAO")
	private UserDAO dao;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	
	
	/**
	 * Creates a {@link spring.model.User} with the "ROLE_IDEATER" role and saves it to a 
	 * database.
	 * 
	 * @param userDetails The UserDetails containing information for creating a 
	 * {@link spring.model.User}.
	 */
	@Transactional
	@Override
	public void createUser(UserDetails userDetails) {
		spring.model.User user = new spring.model.User(userDetails.getUsername(),
										  			   passwordEncoder.encode(userDetails.getPassword()),
										  			   userDetails.isEnabled());
		user.addUserRole(new UserRole(user, "ROLE_IDEATER"));
		dao.save(user);
	}
	
	/**
	 * Loads a {@link spring.model.User} from a database and converts it to a UserDetails.
	 * 
	 * @param username The String representing the username of the {@link spring.model.User} to
	 * be loaded.
	 * @throws UsernameNotFoundException
	 */
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		spring.model.User user = dao.loadById(username);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRoles());
		return buildUserForAuthentication(user, authorities);
	}
	
	private User buildUserForAuthentication(spring.model.User user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}
	
	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);
		return result;
	}

	@Override
	public void updateUser(UserDetails user) {
		
	}

	@Override
	public void deleteUser(String username) {
		
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		
	}

	@Override
	public boolean userExists(String username) {
		return false;
	}
}
