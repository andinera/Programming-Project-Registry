package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import spring.builder.DevelopmentBuilder;
import spring.dao.DevelopmentDAO;
import spring.model.Development;
import spring.model.Idea;
import spring.model.User;


@Component("developmentService")
@ComponentScan("spring.builder")
public class DevelopmentService {
	
	@Autowired
	private DevelopmentBuilder developmentBuilder;
	
	@Autowired
	private DevelopmentDAO developmentDAO;
	
	
	@Transactional
	public void saveDevelopment(Development development) {
		developmentDAO.saveDevelopment(development);
	}
	
	public Development buildDevelopment(Idea idea, String link, User user) {
		return developmentBuilder.setDeveloper(user)
								 .setIdea(idea)
								 .setLink(link)
								 .build();
	}
}
