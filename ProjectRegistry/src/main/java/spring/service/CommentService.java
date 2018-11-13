package spring.service;

import java.util.GregorianCalendar;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.dao.CommentDAO;
import spring.model.Comment;
import spring.model.User;


@Service("commentService")
public class CommentService {
	
	@Autowired
	private CommentDAO dao;
	
	
	@Transactional
	public void save(Comment comment) {
		dao.save(comment);
	}
	
	@Transactional 
	public void update(Comment comment) {
		dao.update(comment);
	}
	
	public Comment create(User commenter, String comment) {
		return new Comment(commenter, new GregorianCalendar(), comment);
	}
	
	@Transactional(readOnly=true)
	public Comment loadById(int id) {
		Comment comment = dao.loadById(id);
		Hibernate.initialize(comment.getVotes());
		return comment;
	}
}
