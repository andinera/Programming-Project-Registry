package spring.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	private String username;
	private String password;
	private boolean enabled;
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);
	private Set<Idea> ideas = new HashSet<Idea>(0);
	private Set<Development> developments = new HashSet<Development>(0);
	
	
	public User() {
	}
	
	public User(String username, String password, boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}
	
	public User(String username, String password, boolean enabled, HashSet<UserRole> userRoles) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.userRoles = userRoles;
	}
	
	@Id
	@Column(name="username", unique=true, nullable=false)
	public String getUsername() {
		return this.username;
	}
	
	public User setUsername(String username) {
		this.username = username;
		return this;
	}
	
	@Column(name="password", nullable=false, length=60)
	public String getPassword() {
		return this.password;
	}
	
	public User setPassword(String password) {
		this.password = password;
		return this;
	}
	
	@Column(name="enabled", nullable=false)
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public User setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="user", cascade=CascadeType.ALL)
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}
	
	public User setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
		return this;
	}
	
	public User addUserRole(UserRole userRole) {
		this.getUserRoles().add(userRole);
		return this;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="poster", cascade=CascadeType.ALL)
	public Set<Idea> getIdeas() {
		return this.ideas;
	}
	
	public User setIdeas(Set<Idea> ideas) {
		this.ideas = ideas;
		return this;
	}
	
	public User addIdea(Idea idea) {
		this.getIdeas().add(idea);
		return this;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="developer", cascade=CascadeType.ALL)
	public Set<Development> getDevelopments() {
		return this.developments;
	}
	
	public User setDevelopments(Set<Development> developments) {
		this.developments = developments;
		return this;
	}
	
	public User addDevelopment(Development development) {
		this.developments.add(development);
		return this;
	}
}
