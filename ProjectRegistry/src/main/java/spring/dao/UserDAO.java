package spring.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spring.model.User;


@Repository("userDAO")
public class UserDAO extends DAO {
	
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
	
	@Transactional
	@SuppressWarnings("unchecked")
	public List<User> loadAll() {
		List<User> users = sessionFactory.getCurrentSession()
										 .createQuery("select distinct u from User u"
										 		+ " left join fetch u.ideas i"
										 		+ " left join fetch u.developments d"
										 		+ " left join fetch i.votes"
										 		+ " left join fetch d.votes")
										 .list();
		return users;
	}
	
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
