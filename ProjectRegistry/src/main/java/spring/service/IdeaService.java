package spring.service;

import java.util.List;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Hashtable;

import javax.servlet.http.HttpSession;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.dao.IdeaDAO;
import spring.model.Idea;
import spring.model.User;
import spring.proxy.Proxy;


@Service("ideaService")
public class IdeaService {
	
	@Autowired
	private IdeaDAO dao;
	
	private Map<String, Proxy<Idea>> proxies = new Hashtable<String, Proxy<Idea>>();

	
	@Transactional
	public int save(Idea idea) {
		return dao.save(idea);
	}
	
	@Transactional
	public void update(Idea idea) {
		dao.update(idea);
	}
	
	public Idea create(String title, String description, User poster) {
		return new Idea(title, description, poster, new GregorianCalendar());
	}
	
	@Transactional
	public Idea loadById(int id) {
		Idea idea = dao.loadById(id);
		return idea;
	}
	
	@Transactional
	public void setProxy(HttpSession session) {
		List<Idea> ideas = dao.loadAll();
		for (Idea idea: ideas) {
			Hibernate.initialize(idea.getVotes());
		}
		ideas.sort(new IdeaComparator());
		proxies.put(session.getId(), new Proxy<Idea>());
		proxies.get(session.getId()).setData(ideas);
		session.setAttribute("numHomePages", proxies.get(session.getId()).getNumPages());
	}
	
	public List<Idea> getProxy(HttpSession session, Integer homePage) {
		List<Idea> ideas = proxies.get(session.getId()).getDataByPage(homePage);
		session.setAttribute("homePage", proxies.get(session.getId()).getPage());
		return ideas;
	}
	
	@Transactional
	public void setProxyWithDateFilter(HttpSession session, GregorianCalendar startCal, GregorianCalendar stopCal) {
		List<Idea> ideas = dao.loadWithDateFilter(startCal, stopCal);
		for (Idea idea: ideas) {
			Hibernate.initialize(idea.getVotes());
		}
		ideas.sort(new IdeaComparator());
		proxies.put(session.getId(), new Proxy<Idea>());
		proxies.get(session.getId()).setData(ideas);
		session.setAttribute("numHomePages", proxies.get(session.getId()).getNumPages());
	}
}

class IdeaComparator implements Comparator<Idea> {
	@Override
	public int compare(Idea left, Idea right) {
		return Integer.compare(right.voteCount(), left.voteCount());
	}
}
