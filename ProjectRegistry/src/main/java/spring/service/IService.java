package spring.service;

import java.util.Set;

import spring.model.Idea;
import spring.model.User;
import spring.pager.Pager;

public interface IService {

	public Idea create(String title, String description, User poster);
	public void add(Idea idea);
	public Pager<Idea> loadByPage(String sessionId, int page);
	public Pager<Idea> loadByPage(String sessionId, Set<Idea> ideas, int page);
}
