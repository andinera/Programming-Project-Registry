package spring.service;

import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import spring.dao.CommentDAO;
import spring.model.Comment;
import spring.model.Idea;
import spring.model.User;
import spring.proxy.Proxy;


@Service("commentService")
public class CommentService {
	
	@Autowired
	@Qualifier("commentDAO")
	private CommentDAO dao;
	
	private Map<String, Proxy<Comment>> sessionProxies = new Hashtable<String, Proxy<Comment>>();
	
	
	public void save(Comment comment) {
		dao.save(comment);
	}
	
	public void update(Comment comment) {
		dao.update(comment);
	}
	
	public void saveOrUpdate(Comment comment) {
		dao.saveOrUpdate(comment);
	}
	
	public Comment create(User commenter, String comment) {
		return new Comment(commenter, new GregorianCalendar(), comment);
	}
	
	public Comment loadById(String sessionId, int id) {
		Proxy<Comment> proxy = sessionProxies.get(sessionId);
		Set<Comment> comments = proxy.getPagedData();
		for (Comment comment : comments) {
			if (comment.getId() == id) {
				return comment;
			}
		}
		Comment comment = dao.loadById(id);
		return comment;
	}
	
	public Proxy<Comment> loadByPage(String sessionId, Idea idea, int page) {
		Proxy<Comment> proxy = sessionProxies.get(sessionId);
		if (proxy == null) {
			proxy = new Proxy<Comment>();
			sessionProxies.put(sessionId, proxy);
		}
		proxy.setPagedData(idea.getComments(), page);
		return proxy;
		
	}
}