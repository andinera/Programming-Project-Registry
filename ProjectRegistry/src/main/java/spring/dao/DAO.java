package spring.dao;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * A class providing the Hibernate transaction actions.
 * 
 * @author Shane Lockwood
 *
 */
@Repository("dAO")
public class DAO implements IDAO {

	@Autowired
	protected SessionFactory sessionFactory;
	
	
	/**
	 * Transitions an Object from transient to persistent and returns the object's id.
	 * 
	 * @param object The Object to be saved.
	 * @return object's id
	 */
	@Transactional
	public Serializable save(Object object) {
		return sessionFactory.getCurrentSession().save(object);
	}
	
	/**
	 * Transitions an Object from detached to persistent and updates the saved copy with the 
	 * updated Object.
	 * 
	 * @param object The Object to be updated.
	 */
	@Transactional
	public void update(Object object) {
		sessionFactory.getCurrentSession().update(object);
	}
	
	/**
	 * A universal method combining save and update.
	 * 
	 * @param object The Object to be saved or updated.
	 * @see spring.dao.DAO#save(Object)
	 * spring.dao.DAO#update(Object)
	 */
	@Transactional
	public void saveOrUpdate(Object object) {
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}
	
	/**
	 * Transitions an Object from transient to persistent.
	 * 
	 * @param object The Object to be persisted.
	 */
	@Transactional
	public void persist(Object object) {
		sessionFactory.getCurrentSession().persist(object);
	}
	
	/**
	 * Transitions an Object from detached to persistent and updates the saved copy with the 
	 * merged Object.
	 * 
	 * @param object The Object to be merged.
	 * @return The persistent Object.
	 */
	@Transactional
	public Object merge(Object object) {
		return sessionFactory.getCurrentSession().merge(object);
	}
}
