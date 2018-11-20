package spring.dao;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("dAO")
public class DAO {

	@Autowired
	protected SessionFactory sessionFactory;
	
	
	@Transactional
	public Serializable save(Object object) {
		return sessionFactory.getCurrentSession().save(object);
	}
	
	@Transactional
	public void update(Object object) {
		sessionFactory.getCurrentSession().update(object);
	}
	
	@Transactional
	public void saveOrUpdate(Object object) {
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}
	
	@Transactional
	public void persist(Object object) {
		sessionFactory.getCurrentSession().persist(object);
	}
	
	@Transactional
	public Object merge(Object object) {
		return sessionFactory.getCurrentSession().merge(object);
	}
}
