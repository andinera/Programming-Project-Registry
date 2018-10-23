package spring.builder;

import org.springframework.stereotype.Component;

import spring.model.Development;
import spring.model.Idea;
import spring.model.User;


@Component("developmentBuilder")
public class DevelopmentBuilder {

	private Development development = new Development();
	
	
	public DevelopmentBuilder setId(int id) {
		development.setId(id);
		return this;
	}
	
	public DevelopmentBuilder setIdea(Idea idea) {
		development.setIdea(idea);
		return this;
	}
	
	public DevelopmentBuilder setLink(String link) {
		development.setLink(link);
		return this;
	}
	
	public DevelopmentBuilder setDeveloper(User developer) {
		development.setDeveloper(developer);
		return this;
	}
	
	public Development build() {
		Development newDevelopment = development;
		development = new Development();
		return newDevelopment;
	}
}
