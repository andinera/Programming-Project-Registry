package spring.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spring.model.User;


/**
 * Loads {@link spring.model.User}s from a database.
 * 
 * @author Shane Lockwood
 *
 */
@Repository("userDAO")
public class UserDAO extends DAO {
	
	/**
	 * Loads a single {@link spring.model.User} from a database.
	 * 
	 * @param id The String which identifies the {@link spring.model.User} to load from a database.
	 * @return {@link spring.model.User}
	 */
	@Transactional
	public User loadById(Serializable id) {
		User user = (User) sessionFactory.getCurrentSession()
										 .createQuery("select distinct u from User u"
										 		+ " left join fetch u.userRoles"
												+ " left join fetch u.ideas i"
												+ " left join fetch u.developments d"
												+ " left join fetch i.votes"
												+ " left join fetch d.votes"
												+ " left join fetch d.idea"
												+ " where u.username=:username")
										 .setParameter("username", (String) id)
										 .uniqueResult();
		return user;
	}
	
	/**
	 * Loads all {@link spring.model.User}s with the keyword as a subset of their usernames.
	 * 
	 * @param keyword The String to compare with the usernames.
	 * @return List&lt;{@link spring.model.User}&gt;
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public List<User> loadByKeyword(String keyword) {
		List<User> users = sessionFactory.getCurrentSession()
										 .createQuery("select distinct u from User u"
										 		+ " left join fetch u.ideas i"
										 		+ " left join fetch u.developments d"
										 		+ " left join fetch i.votes"
										 		+ " left join fetch d.votes"
												+ " where u.username like :keyword")
										 .setParameter("keyword", "%" + keyword + "%")
										 .list();
		return users;
	}
}
