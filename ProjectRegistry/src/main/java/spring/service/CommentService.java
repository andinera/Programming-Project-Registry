package spring.service;

import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.dao.CommentDAO;
import spring.model.Comment;
import spring.model.User;
import spring.proxy.Proxy;


@Service("commentService")
public class CommentService {
	
	@Autowired
	private CommentDAO dao;
	
	private Map<String, Proxy<Comment>> proxies = new Hashtable<String, Proxy<Comment>>();
	
	
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
	
	public void setProxy(HttpSession session, List<Comment> comments) {
		comments.sort(new CommentComparator());
		proxies.put(session.getId(), new Proxy<Comment>());
		proxies.get(session.getId()).setData(comments);
		session.setAttribute("numCommentPages", proxies.get(session.getId()).getNumPages());
	}
	
	public List<Comment> getProxy(HttpSession session, Integer commentPage) {
		List<Comment> comments = proxies.get(session.getId()).getDataByPage(commentPage);
		session.setAttribute("commentPage", proxies.get(session.getId()).getPage());
		return comments;
	}
}

class CommentComparator implements Comparator<Comment> {
	@Override
	public int compare(Comment left, Comment right) {
		return Integer.compare(right.voteCount(), left.voteCount());
	}
}