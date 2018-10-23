package spring.service;

import java.util.List;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import spring.builder.IdeaBuilder;
import spring.dao.IdeaDAO;
import spring.model.Idea;
import spring.model.User;
import spring.model.Development;
import spring.model.Comment;


@Component("ideaService")
@ComponentScan("spring.builder")
public class IdeaService {

	@Autowired
	public IdeaBuilder ideaBuilder;
	
	@Autowired
	private IdeaDAO ideaDAO;

	
	@Transactional
	public int saveIdea(Idea idea) {
		return ideaDAO.saveIdea(idea);
	}
	
	@Transactional
	public void updateIdea(Idea idea) {
		ideaDAO.updateIdea(idea);
	}
	
	public Idea buildIdea(String title, String description, User poster) {
		ideaBuilder.setTitle(title);
		ideaBuilder.setDescription(description);
		ideaBuilder.setPoster(poster);
		Calendar datePosted = new GregorianCalendar();
		ideaBuilder.setDatePosted(datePosted);
		ideaBuilder.setDateModified(datePosted);
		return ideaBuilder.build();
	}
	
	@Transactional(readOnly=true)
	public Idea loadIdeaById(int id) {
		Idea idea = ideaDAO.loadIdeaById(id);
		Hibernate.initialize(idea.getPoster());
		Hibernate.initialize(idea.getVotes());
		Hibernate.initialize(idea.getDevelopments());
		for(Development development : idea.getDevelopments()) {
			Hibernate.initialize(development.getIdea());
			Hibernate.initialize(development.getDeveloper());
			Hibernate.initialize(development.getVotes());
		}
		Hibernate.initialize(idea.getComments());
		for(Comment comment : idea.getComments()) {
			Hibernate.initialize(comment.getCommenter());
			Hibernate.initialize(comment.getVotes());
		}
		return idea;
	}
	
	@Transactional(readOnly=true)
	public List<Idea> loadIdeasByFilter(Date startDate, Date stopDate) {
		GregorianCalendar startCal = new GregorianCalendar();
		startCal.setTime(startDate);
		GregorianCalendar stopCal = new GregorianCalendar();
		stopCal.setTime(stopDate);
		List<Idea> ideas = ideaDAO.loadIdeasByFilter(startCal, stopCal);
		for (Idea idea: ideas) {
			Hibernate.initialize(idea.getPoster());
			Hibernate.initialize(idea.getVotes());
		}
		return ideas;
	}
	
	@Transactional(readOnly=true)
	public List<Idea> loadAllIdeas() {
		List<Idea> ideas = ideaDAO.loadAllIdeas();
		for (Idea idea: ideas) {
			Hibernate.initialize(idea.getPoster());
			Hibernate.initialize(idea.getVotes());
		}
		return ideas;
	}
}
