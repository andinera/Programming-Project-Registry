package spring.service;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.dao.DevelopmentDAO;
import spring.model.Development;
import spring.model.Idea;
import spring.model.User;
import spring.proxy.Proxy;


@Service("developmentService")
public class DevelopmentService {
	
	@Autowired
	private DevelopmentDAO dao;
	
	private Map<String, Proxy<Development>> proxies = new Hashtable<String, Proxy<Development>>();
	
	
	@Transactional
	public void save(Development development) {
		dao.save(development);
	}
	
	@Transactional
	public void update(Development development) {
		dao.update(development);
	}
	
	public Development create(Idea idea, String link, User user) {
		return new Development(user, idea, link);
	}
	
	@Transactional
	public Development loadById(int id) {
		Development development = dao.loadById(id);
		Hibernate.initialize(development.getVotes());
		return development;
	}
	
	public void setProxy(HttpSession session, List<Development> developments) {
		proxies.put(session.getId(), new Proxy<Development>());
		proxies.get(session.getId()).setData(developments);
		session.setAttribute("numDevelopmentPages", proxies.get(session.getId()).getNumPages());
	}
	
	public List<Development> getProxy(HttpSession session, Integer developmentPage) {
		List<Development> developments = proxies.get(session.getId()).getDataByPage(developmentPage);
		session.setAttribute("developmentPage", proxies.get(session.getId()).getPage());
		return developments;
	}
}
