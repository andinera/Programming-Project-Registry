package spring.dao;

import java.io.Serializable;

public interface IDAO {

	public Serializable save(Object object);
	public void update(Object object);
	public void saveOrUpdate(Object object);
	public Object loadById(Serializable id);
}
