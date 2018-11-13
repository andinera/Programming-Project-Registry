package spring.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import spring.model.*;


@Service("serviceFacade")
public class ServiceFacade {

	@Autowired
	private IdeaService ideaService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VoteService voteService;
	
	@Autowired
	private DevelopmentService developmentService;
	
	@Autowired
	private CommentService commentService;
	
	
	// ### Idea ###
	@Transactional
	public void createIdea(String title, String description, String username) {
		User user = userService.loadByUsername(username);
		Idea idea = ideaService.create(title, description, user);
		userService.update(user.addIdea(idea));
	}
	
	@Transactional
	public Idea loadIdea(HttpSession session, int id) {
		Idea idea = ideaService.loadById(id);	
		Hibernate.initialize(idea.getVotes());
		Hibernate.initialize(idea.getDevelopments());
		for(Development development : idea.getDevelopments()) {
			Hibernate.initialize(development.getVotes());
		}
		developmentService.setProxy(session, idea.getDevelopments());
		idea.setDevelopments(developmentService.getProxy(session, 1));
		Hibernate.initialize(idea.getComments());
		for(Comment comment : idea.getComments()) {
			Hibernate.initialize(comment.getVotes());
		}
		commentService.setProxy(session, idea.getComments());
		idea.setComments(commentService.getProxy(session, 1));
		return idea;
	}
	
	public Idea loadIdeaByDevelopmentProxy(HttpSession session, int developmentPage) {
		Idea idea = (Idea)session.getAttribute("idea");
		idea.setDevelopments(developmentService.getProxy(session, developmentPage));
		return idea;
	}
	
	public Idea loadIdeaByCommentProxy(HttpSession session, int commentPage) {
		Idea idea = (Idea)session.getAttribute("idea");
		idea.setComments(commentService.getProxy(session,  commentPage));
		return idea;
	}
	
	@Transactional
	public List<Idea> loadIdeas(HttpSession session) {
		ideaService.setProxy(session);
		return loadIdeasByProxy(session, 1);
	}
	
	@Transactional
	public List<Idea> loadIdeasByProxy(HttpSession session, int page) {
		return ideaService.getProxy(session, page);
	}
	
	// ### User ###
	@Transactional
	public User createUser() {
		return userService.create();
	}
	
	@Transactional
	public void saveUser(String username, String password) {
		userService.save(username, password);
	}
	
	@Transactional
	public User loadUser(String username) {
		return userService.loadByUsername(username);
	}
	
	@Transactional
	public List<User> loadUsersBySearch(String keyword) {
		return userService.loadBySearch(keyword);
	}
	
	// ### Development ###
	@Transactional
	public void createDevelopment(int ideaId, String link, String username) {
		User user = userService.loadByUsername(username);
		Idea idea = ideaService.loadById(ideaId);
		Development development = developmentService.create(idea, link, user);
		idea.addDevelopment(development);
		user.addDevelopment(development);
		ideaService.update(idea);
		userService.update(user);
	}
	
	// ### Comment ###
	@Transactional
	public void createComment(int ideaId, String username, String comment) {
		Idea idea = ideaService.loadById(ideaId);
		User commenter = userService.loadByUsername(username);
		Comment c = commentService.create(commenter, comment);
		idea.addComment(c);
		ideaService.update(idea);
	}
	
	// ### IdeaVote ###
	@Transactional
	public void createIdeaVote(String voter, Boolean upVote, int ideaId) {
		User votee = userService.loadByUsername(voter);
		Idea idea = ideaService.loadById(ideaId);
		IdeaVote vote = voteService.create(votee, upVote, idea);
		idea.addVote(vote);
		ideaService.update(idea);
	}
	
	// ### DevelopmentVote ###
	@Transactional
	public void createDevelopmentVote(String username, Boolean upVote, int developmentId) {
		User voter = userService.loadByUsername(username);
		Development development = developmentService.loadById(developmentId);
		DevelopmentVote vote = voteService.create(voter, upVote, development);
		development.addVote(vote);
		developmentService.update(development);
	}
	
	// ### CommentVote ###
	@Transactional
	public void createCommentVote(String username, Boolean upVote, int commentId) {
		User voter = userService.loadByUsername(username);
		Comment comment = commentService.loadById(commentId);
		CommentVote vote = voteService.create(voter, upVote, comment);
		comment.addVote(vote);
		commentService.update(comment);
	}
}
