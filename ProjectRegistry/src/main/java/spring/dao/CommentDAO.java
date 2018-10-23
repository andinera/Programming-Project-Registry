package spring.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.model.Comment;

@Repository
public class CommentDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	public void saveComment(Comment comment) {
		sessionFactory.getCurrentSession().save(comment);
	}
	
	public void updateComment(Comment comment) {
		sessionFactory.getCurrentSession().update(comment);
	}
	
	@SuppressWarnings("unchecked")
	public Comment loadCommentById(int id) {
		List<Comment> comments = sessionFactory.getCurrentSession()
											   .createQuery("from Comment where id=:id")
											   .setParameter("id",  id)
											   .list();
		return comments.get(0);
	}
}
