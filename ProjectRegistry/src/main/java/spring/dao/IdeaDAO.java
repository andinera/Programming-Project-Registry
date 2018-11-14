package spring.dao;

import java.util.List;
import java.util.GregorianCalendar;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.model.Idea;


@Repository
public class IdeaDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public int save(Idea idea) {
		return (int)sessionFactory.getCurrentSession().save(idea);
	}
	
	public void update(Idea idea) {
		sessionFactory.getCurrentSession().update(idea);
	}

	@SuppressWarnings("unchecked")
	public Idea loadById(int id) {
		List<Idea> ideas = sessionFactory.getCurrentSession()
									 	 .createQuery("from Idea where id=:id")
										 .setParameter("id",  id)
										 .list();
		return ideas.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<Idea> loadWithDateFilter(GregorianCalendar startDate, GregorianCalendar stopDate) {
		List<Idea> ideas = sessionFactory.getCurrentSession()
				.createQuery("from Idea where datePosted between :startDate and :stopDate")
				.setParameter("startDate",  startDate)
				.setParameter("stopDate", stopDate)
				.list();
		return ideas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Idea> loadAll() {
		List<Idea> ideas = sessionFactory.getCurrentSession()
				.createQuery("from Idea")
				.list();
		return ideas;
	}
}
