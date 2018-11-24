package spring.service;

import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import spring.comparator.CommentComparatorByVote;
import spring.dao.CommentDAO;
import spring.model.Comment;
import spring.model.User;
import spring.proxy.Proxy;


/**
 * Service for handling all requests related to {@link spring.model.Comment}s. Maintains a 
 * proxy of {@link spring.model.Comment}s for each session.
 * 
 * @author Shane Lockwood
 *
 */
@Service("commentService")
public class CommentService {
	
	@Autowired
	@Qualifier("commentDAO")
	private CommentDAO dao;
	
	private Map<String, Proxy<Comment>> sessionProxies = new Hashtable<String, Proxy<Comment>>();
	
	
	/**
	 * Saves the {@link spring.model.Comment} to a database.
	 * 
	 * @param comment The {@link spring.model.Comment} to be saved.
	 */
	public void save(Comment comment) {
		dao.save(comment);
	}
	
	/**
	 * Updates an existing {@link spring.model.Comment} to a database.
	 * 
	 * @param comment The {@link spring.model.Comment} to be updated.
	 */
	public void update(Comment comment) {
		dao.update(comment);
	}
	
	/**
	 * Saves or updates a {@link spring.model.Comment} depending on its current state.
	 * 
	 * @param comment The {@link spring.model.Comment} to be saved or updated.
	 */
	public void saveOrUpdate(Comment comment) {
		dao.saveOrUpdate(comment);
	}
	
	/**
	 * Creates and returns a {@link spring.model.Comment}.
	 * 
	 * @param commenter The {@link spring.model.User} representing the commenter.
	 * @param comment The String representing the comment.
	 * @return {@link spring.model.Comment}
	 */
	public Comment create(User commenter, String comment) {
		return new Comment(commenter, new GregorianCalendar(), comment);
	}
	
	/**
	 * Returns a {@link spring.model.Comment} from the proxied {@link spring.model.Comment}s or
	 * loads it from a database.
	 * 
	 * @param sessionId The String representing the session's id.
	 * @param id The int representing the {@link spring.model.Comment}'s id.
	 * @return {@link spring.model.Comment}
	 */
	public Comment loadById(String sessionId, int id) {
		Proxy<Comment> proxy = sessionProxies.get(sessionId);
		List<Comment> comments = proxy.getPagedData();
		for (Comment comment : comments) {
			if (comment.getId() == id) {
				return comment;
			}
		}
		Comment comment = dao.loadById(id);
		return comment;
	}
	
	/**
	 * Proxies the passed {@link spring.model.Comment}s with the passed page and returns the
	 * {@link spring.proxy.Proxy}. The proxied data is sorted with 
	 * {@link spring.comparator.CommentComparatorByVote}.
	 * 
	 * @param sessionId The String representing the session's id.
	 * @param comments The {@link spring.model.Comment}s to be proxied.
	 * @param page The int representing the subset of data to be stored in the 
	 * {@link spring.proxy.Proxy}.
	 * @return {@link spring.proxy.Proxy}
	 */
	public Proxy<Comment> loadByPage(String sessionId, Set<Comment> comments, int page) {
		Proxy<Comment> proxy = sessionProxies.get(sessionId);
		if (proxy == null) {
			proxy = new Proxy<Comment>();
			sessionProxies.put(sessionId, proxy);
		}
		Set<Comment> orderedComments = new TreeSet<Comment>(new CommentComparatorByVote());
		orderedComments.addAll(comments);
		proxy.setPagedData(orderedComments, page);
		return proxy;
		
	}
}