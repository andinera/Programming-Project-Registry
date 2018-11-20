package spring.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spring.model.Comment;


@Repository("commentDAO")
public class CommentDAO extends DAO {
	
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
	
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Comment> loadAll() {
		List<Comment> comments = sessionFactory.getCurrentSession()
				 .createQuery("from Comment")
				 .list();
		return comments;
	}
}
