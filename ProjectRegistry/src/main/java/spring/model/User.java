package spring.model;

import java.util.HashSet;
import java.util.TreeSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import spring.comparator.DevelopmentComparatorByVote;
import spring.comparator.IdeaComparatorByVote;


/**
 * Models a user with mappings to their contributions.
 * 
 * @author Shane Lockwood
 *
 */
@Entity
@Table(name="users")
public class User {
	
	private String username;
	private String password;
	private boolean enabled;
	private Set<UserRole> userRoles = new HashSet<UserRole>();
	private Set<Idea> ideas = new TreeSet<Idea>(new IdeaComparatorByVote());
	private Set<Development> developments = new TreeSet<Development>(new DevelopmentComparatorByVote());
	
	
	@SuppressWarnings("unused")
	private User() {
	}
	
	/**
	 * Constructs a User.
	 * 
	 * @param username The String representing the username.
	 * @param password The String representing the password.
	 * @param enabled The boolean representing if the user's account is enabled.
	 */
	public User(String username, String password, boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}
	
	/**
	 * Returns the username.
	 * 
	 * @return String
	 */
	@Id
	@Column(name="username", unique=true, nullable=false)
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Sets/modifies the username.
	 * 
	 * @param username The String representing the username.
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Returns the password.
	 * 
	 * @return String
	 */
	@Column(name="password", nullable=false, length=60)
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Sets/modifies the password.
	 * 
	 * @param password The String representing the password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Returns if the account is enabled.
	 * 
	 * @return boolean
	 */
	@Column(name="enabled", nullable=false)
	public boolean isEnabled() {
		return this.enabled;
	}
	
	/**
	 * Sets/modifies the account's enabled status.
	 * 
	 * @param enabled The boolean representing the account's status.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * Returns the mapped {@link spring.model.UserRole}s.
	 * 
	 * @return Set&lt;{@link spring.model.UserRole}&gt;
	 */
	@OneToMany(fetch=FetchType.LAZY, mappedBy="user", cascade=CascadeType.ALL)
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}
	
	@SuppressWarnings("unused")
	private void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	/**
	 * Appends the {@link spring.model.UserRole} to the List of previous user roles.
	 * 
	 * @param userRole The {@link spring.model.UserRole} representing the inserted user role.
	 */
	public void addUserRole(UserRole userRole) {
		this.getUserRoles().add(userRole);
	}
	
	/**
	 * Returns the mapped {@link spring.model.Idea}s.
	 * 
	 * @return Set&lt;{@link spring.model.Idea}&gt;
	 */
	@OneToMany(fetch=FetchType.LAZY, mappedBy="poster", cascade=CascadeType.ALL)
	public Set<Idea> getIdeas() {
		return this.ideas;
	}
	
	@SuppressWarnings("unused")
	private void setIdeas(Set<Idea> ideas) {
		this.ideas = ideas;
	}
	
	/**
	 * Appends the {@link spring.model.Idea} to the List of previous ideas.
	 * 
	 * @param idea The {@link spring.model.Idea} to append.
	 */
	public void addIdea(Idea idea) {
		this.getIdeas().add(idea);
	}
	
	/**
	 * Returns the mapped developments.
	 * 
	 * @return Set&lt;{@link spring.model.Development}&gt;
	 */
	@OneToMany(fetch=FetchType.LAZY, mappedBy="developer", cascade=CascadeType.ALL)
	public Set<Development> getDevelopments() {
		return this.developments;
	}
	
	@SuppressWarnings("unused")
	private void setDevelopments(Set<Development> developments) {
		this.developments = developments;
	}
	
	/**
	 * Appends the {@link spring.model.Development} to the List of previous developments. If a 
	 * {@link spring.model.Development} mapped to the same 
	 * {@link spring.model.User} exists, nothing happens.
	 * 
	 * @param development The {@link spring.model.Development} to append.
	 * @return If successfully inserted, return the new {@link spring.model.Development}, 
	 * otherwise return the existing {@link spring.model.Development}.
	 */
	public Development addDevelopment(Development development) {
		for (Development d : getDevelopments()) {
			if (d.getDeveloper().getUsername().equals(development.getDeveloper().getUsername())
				&& d.getIdea().getId() == development.getIdea().getId()) {
				development = d;
					return development;
				}
		}
		this.developments.add(development);
		return development;
	}
}
