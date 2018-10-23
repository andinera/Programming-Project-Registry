package spring.dao;

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
}
