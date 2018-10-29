package spring.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.model.Development;


@Repository
public class DevelopmentDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	public void save(Development development) {
		sessionFactory.getCurrentSession().save(development);
	}
	
	public void update(Development development) {
		sessionFactory.getCurrentSession().update(development);
	}
	
	@SuppressWarnings("unchecked")
	public Development loadById(int id) {
		List<Development> developments = sessionFactory.getCurrentSession()
													   .createQuery("from Development where id=:id")
													   .setParameter("id",  id)
													   .list();
		return developments.get(0);
	}
}
