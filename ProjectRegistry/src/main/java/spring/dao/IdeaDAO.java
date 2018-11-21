package spring.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spring.model.Idea;


/**
 * Loads {@link spring.model.Idea}s from a database.
 * 
 * @author Shane Lockwood
 *
 */
@Repository("ideaDAO")
public class IdeaDAO extends DAO {
	
	/**
	 * Loads a single {@link spring.model.Idea} from a database.
	 * 
	 * @param id The Integer which identifies the {@link spring.model.Idea} to load from a 
	 * database.
	 * @return {@link spring.model.Idea}
	 */
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
	
	/**
	 * Loads all {@link spring.model.Idea}s from a database.
	 * 
	 * @return List&lt;{@link spring.model.Idea}&gt;
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Idea> loadAll() {
		List<Idea> ideas = sessionFactory.getCurrentSession()
										 .createQuery("select distinct i from Idea i"
										 		+ " left join fetch i.poster"
										 		+ " left join fetch i.votes"
									 	 		+ " left join fetch i.developments d"
									 	 		+ " left join fetch i.comments c"
									 	 		+ " left join fetch d.votes"
									 	 		+ " left join fetch c.votes")
										 .list();
		return ideas;
	}
}
