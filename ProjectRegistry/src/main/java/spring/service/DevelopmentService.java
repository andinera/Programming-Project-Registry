package spring.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.dao.DevelopmentDAO;
import spring.model.Development;
import spring.model.Idea;
import spring.model.User;


@Service("developmentService")
public class DevelopmentService {
	
	@Autowired
	private DevelopmentDAO dao;
	
	
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
	
	@Transactional(readOnly=true)
	public Development loadById(int id) {
		Development development = dao.loadById(id);
		Hibernate.initialize(development.getVotes());
		return development;
	}
}
