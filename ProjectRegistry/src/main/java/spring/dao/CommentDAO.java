package spring.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spring.model.Comment;


/**
 * Loads {@link spring.model.Comment}s from a database.
 * 
 * @author Shane Lockwood
 *
 */
@Repository("commentDAO")
public class CommentDAO extends DAO {
	
	/**
	 * Loads a single {@link spring.model.Comment} from a database.
	 * 
	 * @param id The Integer which identifies the {@link spring.model.Comment} to load from a 
	 * database.
	 * @return {@link spring.model.Comment}
	 */
	@Transactional
	public Comment loadById(Serializable id) {
		Comment comment = (Comment) sessionFactory.getCurrentSession()
											   .createQuery("select distinct c from Comment c"
											   		+ " left join fetch c.votes"
											   		+ " where c.id=:id")
											   .setParameter("id", (int) id)
											   .uniqueResult();
		return comment;
	}
}
