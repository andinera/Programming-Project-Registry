package spring.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.model.Idea;


@Repository
public class IdeaDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public int saveIdea(Idea idea) {
		return (int)sessionFactory.getCurrentSession().save(idea);
	}
	
	public void updateIdea(Idea idea) {
		sessionFactory.getCurrentSession().update(idea);
	}

	@SuppressWarnings("unchecked")
	public Idea loadIdeaById(int id) {
		List<Idea> ideas = sessionFactory.getCurrentSession()
				.createQuery("from Idea where id=:id")
				.setParameter("id",  id)
				.list();
		Idea idea = ideas.get(0);
		return idea;
	}
	
	@SuppressWarnings("unchecked")
	public List<Idea> loadAllIdeas() {
		List<Idea> ideas = sessionFactory.getCurrentSession()
				.createQuery("from Idea")
				.list();
		return ideas;
	}
}
