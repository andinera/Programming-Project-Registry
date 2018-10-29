package spring.service;

import java.util.List;
import java.util.GregorianCalendar;
import java.util.Date;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import spring.dao.IdeaDAO;
import spring.model.Idea;
import spring.model.User;
import spring.model.Development;
import spring.model.Comment;


@Component("ideaService")
public class IdeaService {
	
	@Autowired
	private IdeaDAO dao;

	
	@Transactional
	public int save(Idea idea) {
		return dao.save(idea);
	}
	
	@Transactional
	public void update(Idea idea) {
		dao.update(idea);
	}
	
	public Idea create(String title, String description, User poster) {
		return new Idea(title, description, poster, new GregorianCalendar());
	}
	
	@Transactional(readOnly=true)
	public Idea loadById(int id) {
		Idea idea = dao.loadById(id);
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
	public List<Idea> loadByFilter(Date startDate, Date stopDate) {
		GregorianCalendar startCal = new GregorianCalendar();
		startCal.setTime(startDate);
		GregorianCalendar stopCal = new GregorianCalendar();
		stopCal.setTime(stopDate);
		List<Idea> ideas = dao.loadByFilter(startCal, stopCal);
		for (Idea idea: ideas) {
			Hibernate.initialize(idea.getPoster());
			Hibernate.initialize(idea.getVotes());
		}
		return ideas;
	}
	
	@Transactional(readOnly=true)
	public List<Idea> loadAll() {
		List<Idea> ideas = dao.loadAll();
		for (Idea idea: ideas) {
			Hibernate.initialize(idea.getPoster());
			Hibernate.initialize(idea.getVotes());
		}
		return ideas;
	}
}
