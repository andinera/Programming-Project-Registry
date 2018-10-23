package spring.builder;

import org.springframework.stereotype.Component;

import spring.model.User;

@Component("userBuilder")
public class UserBuilder {
	
	private String username = null;
	private String password = null;
	
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public User build() {
		User user = new User(username, password, true);
		username = null;
		password = null;
		return user;
	}
}
