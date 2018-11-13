package spring.service;

import java.util.List;
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
	
//	@Transactional
//	public List<Idea> loadByFilter(Date startDate, Date stopDate) {
//		GregorianCalendar startCal = new GregorianCalendar();
//		startCal.setTime(startDate);
//		GregorianCalendar stopCal = new GregorianCalendar();
//		stopCal.setTime(stopDate);
//		List<Idea> ideas = dao.loadByFilter(startCal, stopCal);
//		for (Idea idea: ideas) {
//			Hibernate.initialize(idea.getPoster());
//			Hibernate.initialize(idea.getVotes());
//		}
//		return ideas;
//	}
	
	@Transactional
	public void setProxy(HttpSession session) {
		List<Idea> ideas = dao.loadAll();
		for (Idea idea: ideas) {
			Hibernate.initialize(idea.getVotes());
		}
		proxies.put(session.getId(), new Proxy<Idea>());
		proxies.get(session.getId()).setData(ideas);
		session.setAttribute("numHomePages", proxies.get(session.getId()).getNumPages());
	}
	
	public List<Idea> getProxy(HttpSession session, Integer homePage) {
		List<Idea> ideas = proxies.get(session.getId()).getDataByPage(homePage);
		session.setAttribute("homePage", proxies.get(session.getId()).getPage());
		return ideas;
	}
}
