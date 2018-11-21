package spring.service;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import spring.comparator.DevelopmentComparatorByVote;
import spring.dao.DevelopmentDAO;
import spring.model.Development;
import spring.model.Idea;
import spring.model.User;
import spring.proxy.Proxy;


/**
 * Service for handling all requests related to {@link spring.model.Development}s. Maintains a 
 * proxy of {@link spring.model.Development}s for each session.
 * 
 * @author Shane Lockwood
 *
 */
@Service("developmentService")
public class DevelopmentService {
	
	@Autowired
	@Qualifier("developmentDAO")
	private DevelopmentDAO dao;
	
	private Map<String, Proxy<Development>> sessionProxies = new Hashtable<String, Proxy<Development>>();
	
	
	/**
	 * Saves the {@link spring.model.Development} to a database.
	 * 
	 * @param development The {@link spring.model.Development} to be saved.
	 */
	public void save(Development development) {
		dao.save(development);
	}
	
	/**
	 * Updates an existing {@link spring.model.Development} to a database.
	 * 
	 * @param development The {@link spring.model.Development} to be updated.
	 */
	public void update(Development development) {
		dao.update(development);
	}
	
	/**
	 * Saves or updates a {@link spring.model.Development} depending on its current state.
	 * 
	 * @param development The {@link spring.model.Development} to be saved or updated.
	 */
	public void saveOrUpdate(Development development) {
		dao.saveOrUpdate(development);
	}
	
	/**
	 * Creates and returns a {@link spring.model.Development}.
	 * 
	 * @param idea The {@link spring.model.Idea} representing the associated idea.
	 * @param link The String representing the website's link.
	 * @param user The {@link spring.model.User} representing the developer.
	 * @return {@link spring.model.Development}
	 */
	public Development create(Idea idea, String link, User user) {
		return new Development(user, idea, link);
	}
	
	/**
	 * Returns a {@link spring.model.Development} from the proxied 
	 * {@link spring.model.Development}s or loads it from a database.
	 * 
	 * @param sessionId The String representing the session's id.
	 * @param id The int representing the {@link spring.model.Development}'s id.
	 * @return {@link spring.model.Development}
	 */
	public Development loadById(String sessionId, int id) {
		Proxy<Development> proxy = sessionProxies.get(sessionId);
		Set<Development> developments = proxy.getPagedData();
		for (Development development : developments) {
			if (development.getId() == id) {
				return development;
			}
		}
		Development development = dao.loadById(id);
		return development;
	}
	
	/**
	 * Proxies the passed {@link spring.model.Development}s with the passed page and returns the
	 * {@link spring.proxy.Proxy}. The proxied data is sorted with 
	 * {@link spring.comparator.DevelopmentComparatorByVote}.
	 * 
	 * @param sessionId The String representing the session's id.
	 * @param developments The {@link spring.model.Development}s to be proxied.
	 * @param page The int representing the subset of data to be stored in the 
	 * {@link spring.proxy.Proxy}.
	 * @return {@link spring.proxy.Proxy}
	 */
	public Proxy<Development> loadByPage(String sessionId, Set<Development> developments, int page) {
		Proxy<Development> proxy = sessionProxies.get(sessionId);
		if (proxy == null) {
			proxy = new Proxy<Development>();
			sessionProxies.put(sessionId, proxy);
		}
		Set<Development> orderedDevelopments = new TreeSet<Development>(new DevelopmentComparatorByVote());
		orderedDevelopments.addAll(developments);
		proxy.setPagedData(orderedDevelopments, page);
		return proxy;
	}
}
