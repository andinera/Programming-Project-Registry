package spring.service;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import spring.dao.DevelopmentDAO;
import spring.model.Development;
import spring.model.Idea;
import spring.model.User;
import spring.proxy.Proxy;


@Service("developmentService")
public class DevelopmentService {
	
	@Autowired
	@Qualifier("developmentDAO")
	private DevelopmentDAO dao;
	
	private Map<String, Proxy<Development>> sessionProxies = new Hashtable<String, Proxy<Development>>();
	
	
	public void save(Development development) {
		dao.save(development);
	}
	
	public void update(Development development) {
		dao.update(development);
	}
	
	public void saveOrUpdate(Development development) {
		dao.saveOrUpdate(development);
	}
	
	public Development create(Idea idea, String link, User user) {
		return new Development(user, idea, link);
	}
	
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
	
	public Proxy<Development> loadByPage(String sessionId, Idea idea, int page) {
		Proxy<Development> proxy = sessionProxies.get(sessionId);
		if (proxy == null) {
			proxy = new Proxy<Development>();
			sessionProxies.put(sessionId, proxy);
		}
		proxy.setPagedData(idea.getDevelopments(), page);
		return proxy;
	}
}
