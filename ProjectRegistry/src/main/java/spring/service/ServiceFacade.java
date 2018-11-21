package spring.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import spring.model.*;
import spring.proxy.Proxy;


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
	
	
	// ### User ###
	public void saveUser(String username, String password) {
		userService.save(username, password);
	}
	
	public void loadUser(HttpSession session) {
		User user = userService.loadByUsername((String) session.getAttribute("username"));
		session.setAttribute("user", user);
		Proxy<Idea> ideaProxy = ideaService.loadByPage(session.getId(), user.getIdeas(), (int) session.getAttribute("userPageOfIdeas"));
		session.setAttribute("numIdeaPages", ideaProxy.getNumPages());
		session.setAttribute("ideaPage", ideaProxy.getPage());
		session.setAttribute("ideas", ideaProxy.getPagedData());
		Proxy<Development> developmentProxy = developmentService.loadByPage(session.getId(), user.getDevelopments(), (int) session.getAttribute("userPageOfDevelopments"));
		session.setAttribute("numDevelopmentPages", developmentProxy.getNumPages());
		session.setAttribute("developmentPage", developmentProxy.getPage());
		session.setAttribute("developments", developmentProxy.getPagedData());
	}
	
	public void loadUsersByKeyword(HttpSession session) {
		Proxy<User> proxy = userService.loadByPage(session.getId(), (String) session.getAttribute("keyword"), (int) session.getAttribute("searchPageOfUsers"));
		session.setAttribute("numUserPages", proxy.getNumPages());
		session.setAttribute("userPage", proxy.getPage());
		session.setAttribute("users", proxy.getPagedData());
	}
	
	// ### Idea ###
	public void createIdea(HttpSession session, String title, String description, String username) {
		User user = userService.loadByUsername(username);
		session.setAttribute("user", user);
		Idea idea = ideaService.create(title, description, user);
		user.addIdea(idea);
		userService.saveOrUpdate(user);
		ideaService.add(idea);
	}
	
	public void loadIdea(HttpSession session) {
		Idea idea = ideaService.loadById((int) session.getAttribute("ideaId"));
		session.setAttribute("idea", idea);
		Proxy<Development> developmentProxy = developmentService.loadByPage(session.getId(), idea.getDevelopments(), (int) session.getAttribute("ideaPageOfDevelopments"));
		session.setAttribute("numDevelopmentPages", developmentProxy.getNumPages());
		session.setAttribute("developmentPage", developmentProxy.getPage());
		session.setAttribute("developments", developmentProxy.getPagedData());
		Proxy<Comment> commentProxy = commentService.loadByPage(session.getId(), idea.getComments(), (int) session.getAttribute("ideaPageOfComments"));
		session.setAttribute("numCommentPages", commentProxy.getNumPages());
		session.setAttribute("commentPage", commentProxy.getPage());
		session.setAttribute("comments", commentProxy.getPagedData());
	}
	
	public void loadIdeasByPage(HttpSession session) {
		Proxy<Idea> proxy = ideaService.loadByPage(session.getId(), (int) session.getAttribute("homePageOfIdeas"));
		session.setAttribute("numHomePagesOfIdeas", proxy.getNumPages());
		session.setAttribute("homePageOfIdeas", proxy.getPage());
		session.setAttribute("homeIdeas", proxy.getPagedData());
	}
	
	// ### Development ###
	public void createDevelopment(HttpSession session, String link, String username) {
		User user = userService.loadByUsername(username);
		session.setAttribute("user", user);
		Idea idea = (Idea) session.getAttribute("idea");
		idea = ideaService.loadById(idea.getId());
		session.setAttribute("idea", idea);
		Development development = developmentService.create(idea, link, user);
		for (Development d : idea.getDevelopments()) {
			if (d.getDeveloper().getUsername().equals(user.getUsername())) {
				return;
			}
		}
		development = user.addDevelopment(development);
		idea.addDevelopment(development);
		userService.saveOrUpdate(user);
		ideaService.saveOrUpdate(idea);
	}
	
	// ### Comment ###
	public void createComment(HttpSession session, String username, String comment) {
		User user = userService.loadByUsername(username);
		session.setAttribute("user", user);
		Idea idea = (Idea) session.getAttribute("idea");
		idea = ideaService.loadById(idea.getId());
		session.setAttribute("idea", idea);
		Comment c = commentService.create(user, comment);
		idea.addComment(c);
		ideaService.saveOrUpdate(idea);
	}
	
	// ### IdeaVote ###
	public void createIdeaVote(HttpSession session, String username, Boolean upVote, int ideaId) {
		User user = userService.loadByUsername(username);
		session.setAttribute("user", user);
		Idea idea = (Idea) session.getAttribute("idea");
		idea = ideaService.loadById(idea.getId());
		session.setAttribute("idea", idea);
		IdeaVote vote = voteService.create(user, upVote, idea);
		idea.addVote(vote);
		ideaService.saveOrUpdate(idea);
	}
	
	// ### DevelopmentVote ###
	public void createDevelopmentVote(HttpSession session, String username, Boolean upVote, int developmentId) {
		User user = userService.loadByUsername(username);
		session.setAttribute("user", user);
		Idea idea = (Idea) session.getAttribute("idea");
		idea = ideaService.loadById(idea.getId());
		session.setAttribute("idea", idea);
		Development development = developmentService.loadById(session.getId(), developmentId);
		DevelopmentVote vote = voteService.create(user, upVote, development);
		development.addVote(vote);
		developmentService.saveOrUpdate(development);
	}
	
	// ### CommentVote ###
	public void createCommentVote(HttpSession session, String username, Boolean upVote, int commentId) {
		User user = userService.loadByUsername(username);
		session.setAttribute("user", user);
		Idea idea = (Idea) session.getAttribute("idea");
		idea = ideaService.loadById(idea.getId());
		session.setAttribute("idea", idea);
		Comment comment = commentService.loadById(session.getId(), commentId);
		CommentVote vote = voteService.create(user, upVote, comment);
		comment.addVote(vote);
		commentService.saveOrUpdate(comment);
	}
}
