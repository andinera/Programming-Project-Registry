package spring.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spring.model.Idea;


@Repository("ideaDAO")
public class IdeaDAO extends DAO {
	
	@Transactional
	public Idea loadById(Serializable id) {
		Idea idea = (Idea) sessionFactory.getCurrentSession()
									 	 .createQuery("select distinct i from Idea i"
									 	 		+ " left join fetch i.votes"
									 	 		+ " left join fetch i.developments d"
									 	 		+ " left join fetch i.comments c"
									 	 		+ " left join fetch d.votes"
									 	 		+ " left join fetch c.votes"
									 	 		+ " where i.id=:id")
										 .setParameter("id", (int) id)
										 .uniqueResult();
		return idea;
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Idea> loadAll() {
		List<Idea> ideas = sessionFactory.getCurrentSession()
										 .createQuery("select distinct i from Idea i "
										 		+ "left join fetch i.votes "
									 	 		+ "left join fetch i.developments d "
									 	 		+ "left join fetch i.comments c "
									 	 		+ "left join fetch d.votes "
									 	 		+ "left join fetch c.votes")
										 .list();
		return ideas;
	}
}
