package spring.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.model.Development;


@Repository
public class DevelopmentDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	public void saveDevelopment(Development development) {
		sessionFactory.getCurrentSession().save(development);
	}
}
