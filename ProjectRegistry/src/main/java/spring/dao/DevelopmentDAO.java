package spring.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spring.model.Development;


/**
 * Loads {@link spring.model.Development}s from a database.
 * 
 * @author Shane Lockwood
 *
 */
@Repository("developmentDAO")
public class DevelopmentDAO extends DAO {
	
	/**
	 * Loads a single {@link spring.model.Development} from  adatabase.
	 * 
	 * @param id The Integer which identifies the {@link spring.model.Development} to load from a 
	 * database.
	 * @return {@link spring.model.Development}
	 */
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
}
