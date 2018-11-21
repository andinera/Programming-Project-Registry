package spring.model;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * Models a user role.
 * 
 * @author Shane Lockwood
 *
 */
@Entity
@Table(name="user_roles", uniqueConstraints=@UniqueConstraint(columnNames= {"role", "username"}))
public class UserRole {

	private int userRoleId;
	private User user;
	private String role;
	
	
	@SuppressWarnings("unused")
	private UserRole() {
	}
	
	/**
	 * Constructs a UserRole.
	 * 
	 * @param user The {@link spring.model.User} representing the user.
	 * @param role The String representing the role.
	 */
	public UserRole(User user, String role) {
		this.user = user;
		this.role = role;
	}
	
	/**
	 * Returns the id. The id is auto-generated when this object is persisted.
	 * 
	 * @return int
	 */
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="user_role_id", unique=true, nullable=false)
	public int getUserRoleId() {
		return this.userRoleId;
	}
	
	@SuppressWarnings("unused")
	private void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}
	
	/**
	 * Returns the user.
	 * 
	 * @return {@link spring.model.User}
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="username", nullable=false)
	public User getUser() {
		return this.user;
	}
	
	@SuppressWarnings("unused")
	private void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * Returns the role.
	 * 
	 * @return String
	 */
	@Column(name="role", nullable=false, length=45)
	public String getRole() {
		return this.role;
	}
	
	@SuppressWarnings("unused")
	private void setRole(String role) {
		this.role = role;
	}
}
