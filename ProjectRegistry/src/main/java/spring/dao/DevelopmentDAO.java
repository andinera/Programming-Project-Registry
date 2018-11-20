package spring.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spring.model.Development;


@Repository("developmentDAO")
public class DevelopmentDAO extends DAO {
	
	@Transactional
	public Development loadById(Serializable id) {
		Development development = (Development) sessionFactory.getCurrentSession()
															  .createQuery("select distinct d from Development d"
															  		+ " left join fetch d.votes"
															  		+ " where d.id=:id")
															  .setParameter("id", (int) id)
															  .uniqueResult();
		return development;
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Development> loadAll() {
		List<Development> developments = sessionFactory.getCurrentSession()
											     	   .createQuery("from Development")
											     	   .list();
		return developments;
	}
}
