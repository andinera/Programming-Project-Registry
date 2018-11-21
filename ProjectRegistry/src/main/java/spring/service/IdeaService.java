package spring.service;

import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Hashtable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import spring.comparator.IdeaComparatorByVote;
import spring.dao.IdeaDAO;
import spring.model.Idea;
import spring.model.User;
import spring.proxy.Proxy;


/**
 * Service for handling all requests related to {@link spring.model.Idea}s. Maintains a 
 * proxy of {@link spring.model.Idea}s for each session. Stores a global set of 
 * {@link spring.model.Idea}s for all sessions to access.
 * 
 * @author Shane Lockwood
 *
 */
@Service("ideaService")
public class IdeaService {
	
	@Autowired
	@Qualifier("ideaDAO")
	private IdeaDAO dao;
	
	private Set<Idea> sharedIdeas;
	private Map<String, Proxy<Idea>> sessionProxies = new Hashtable<String, Proxy<Idea>>();
	
	
	/**
	 * Loads all {@link spring.model.Idea}s from a database and stores them to be accessed by
	 * all sessions.
	 */
	@PostConstruct
	public void initialize() {
		List<Idea> dataList = dao.loadAll();
		sharedIdeas = new TreeSet<Idea>(new IdeaComparatorByVote());
		sharedIdeas.addAll(dataList);
	}
	
	/**
	 * Saves the {@link spring.model.Idea} to a database.
	 * 
	 * @param idea The {@link spring.model.Idea} to be saved.
	 */
	@Transactional
	public int save(Idea idea) {
		int id = (int) dao.save(idea);
		sharedIdeas.add(idea);
		return id;
	}
	
	/**
	 * Updates an existing {@link spring.model.Idea} to a database.
	 * 
	 * @param idea The {@link spring.model.Idea} to be updated.
	 */
	@Transactional
	public void update(Idea idea) {
		dao.update(idea);
	} 
	
	/**
	 * Saves or updates a {@link spring.model.Idea} depending on its current state.
	 * 
	 * @param idea The {@link spring.model.Idea} to be saved or updated.
	 */
	public void saveOrUpdate(Idea idea) {
		dao.saveOrUpdate(idea);
		for (Idea i : sharedIdeas) {
			if (i.getId() == idea.getId()) {
				i = idea;
			}
		}
	}
	
	/**
	 * Creates and returns an {@link spring.model.Idea}.
	 * 
	 * @param title The String representing the title.
	 * @param description The String representing the description.
	 * @param poster The {@link spring.model.User} representing the poster.
	 * @return {@link spring.model.Idea}
	 */
	public Idea create(String title, String description, User poster) {
		return new Idea(title, description, poster, new GregorianCalendar());
	}
	
	/**
	 * Appends an {@link spring.model.Idea} to the globally available {@link spring.model.Idea}s.
	 * If the {@link spring.model.Idea} is already stored, do nothing.
	 * 
	 * @param idea The {@link spring.model.Idea} to be stored.
	 */
	public void add(Idea idea) {
		for (Idea i : sharedIdeas) {
			if (i.getId() == idea.getId()) {
				return;
			}
		}
		sharedIdeas.add(idea);
	}
	
	/**
	 * Returns an {@link spring.model.Idea} from the globally shared {@link spring.model.Idea}s or 
	 * loads it from a database.
	 * 
	 * @param id The int representing the {@link spring.model.Idea}'s id.
	 * @return {@link spring.model.Idea}
	 */
	public Idea loadById(int id) {
		for (Idea idea : sharedIdeas) {
			if (idea.getId() == id) {
				return idea;
			}
		}
		Idea idea = dao.loadById(id);
		sharedIdeas.add(idea);
		return idea;
	}
	
	/**
	 * Proxies the globally shared {@link spring.model.Idea}s with the passed page and returns 
	 * the {@link spring.proxy.Proxy}.
	 * 
	 * @param sessionId The String representing the session's id.
	 * @param page The int representing the subset of data to be stored in the 
	 * {@link spring.proxy.Proxy}.
	 * @return {@link spring.proxy.Proxy}
	 */
	public Proxy<Idea> loadByPage(String sessionId, int page) {
		Proxy<Idea> proxy = sessionProxies.get(sessionId);
		if (proxy == null) {
			proxy = new Proxy<Idea>();
			sessionProxies.put(sessionId, proxy);
		}
		Set<Idea> orderedIdeas = new TreeSet<Idea>(new IdeaComparatorByVote());
		orderedIdeas.addAll(sharedIdeas);
		proxy.setPagedData(orderedIdeas, page);
		return proxy;
	}
	
	/**
	 * Proxies the passed {@link spring.model.Idea}s with the passed page and returns 
	 * the {@link spring.proxy.Proxy}. The proxied data is sorted with 
	 * {@link spring.comparator.IdeaComparatorByVote}.
	 * 
	 * @param sessionId The String representing the session's id.
	 * @param ideas The {@link spring.model.Idea}s to be proxied.
	 * @param page The int representing the subset of data to be stored in the 
	 * {@link spring.proxy.Proxy}.
	 * @return {@link spring.proxy.Proxy}
	 */
	public Proxy<Idea> loadByPage(String sessionId, Set<Idea> ideas, int page) {
		Proxy<Idea> proxy = sessionProxies.get(sessionId);
		if (proxy == null) {
			proxy = new Proxy<Idea>();
			sessionProxies.put(sessionId, proxy);
		}
		Set<Idea> orderedIdeas = new TreeSet<Idea>(new IdeaComparatorByVote());
		orderedIdeas.addAll(ideas);
		proxy.setPagedData(orderedIdeas, page);
		return proxy;
	}
}
