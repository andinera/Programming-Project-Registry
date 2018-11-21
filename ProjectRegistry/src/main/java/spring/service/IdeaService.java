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

import spring.Comparator.IdeaComparatorByVote;
import spring.dao.IdeaDAO;
import spring.model.Idea;
import spring.model.User;
import spring.proxy.Proxy;


@Service("ideaService")
public class IdeaService {
	
	@Autowired
	@Qualifier("ideaDAO")
	private IdeaDAO dao;
	
	private Set<Idea> sharedIdeas;
	private Map<String, Proxy<Idea>> sessionProxies = new Hashtable<String, Proxy<Idea>>();
	
	
	@PostConstruct
	public void initialize() {
		List<Idea> dataList = dao.loadAll();
		sharedIdeas = new TreeSet<Idea>(new IdeaComparatorByVote());
		sharedIdeas.addAll(dataList);
	}
	
	@Transactional
	public int save(Idea idea) {
		int id = (int) dao.save(idea);
		sharedIdeas.add(idea);
		return id;
	}
	
	@Transactional
	public void update(Idea idea) {
		dao.update(idea);
	} 
	
	public void saveOrUpdate(Idea idea) {
		dao.saveOrUpdate(idea);
		for (Idea i : sharedIdeas) {
			if (i.getId() == idea.getId()) {
				i = idea;
			}
		}
	}
	
	public Idea merge(Idea idea) {
		idea = (Idea) dao.merge(idea);
		for (Idea i : sharedIdeas) {
			if (i.getId() == idea.getId()) {
				i = idea;
				return idea;
			}
		}
		sharedIdeas.add(idea);
		return idea;
	} 
	
	public Idea create(String title, String description, User poster) {
		return new Idea(title, description, poster, new GregorianCalendar());
	}
	
	public void add(Idea idea) {
		for (Idea i : sharedIdeas) {
			if (i.getId() == idea.getId()) {
				return;
			}
		}
		sharedIdeas.add(idea);
	}
	
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
