package spring.builder;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import spring.model.Idea;
import spring.model.User;

@Component("ideaBuilder")
public class IdeaBuilder {

	private Idea idea = new Idea();
	
	
	public void setId(final int id) {
		idea.setId(id);
	}
	
	public void setTitle(final String title) {
		idea.setTitle(title);
	}
	
	public void setDescription(final String description) {
		idea.setDescription(description);
	}
	
	public void setDatePosted(final Calendar datePosted) {
		idea.setDatePosted(datePosted);
	}
	
	public void setDateModified(final Calendar dateModified) {
		idea.setDateModified(dateModified);
	}
	
	public void setPoster(final User poster) {
		idea.setPoster(poster);
	}
	
	public Idea build() {
		Idea builtIdea = idea;
		idea = new Idea();
		return builtIdea;
	}
	
}
