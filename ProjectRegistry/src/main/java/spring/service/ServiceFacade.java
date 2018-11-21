package spring.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import spring.model.*;
import spring.proxy.Proxy;


/**
 * Facade for managing the IdeaService, UserService, VoteService, DevelopmentService, and
 * CommentService.
 * 
 * @author Shane Lockwood
 *
 */
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
	/**
	 * Saves a {@link spring.model.User}.
	 * 
	 * @param username The String representing the username.
	 * @param password The String representing the password.
	 */
	public void saveUser(String username, String password) {
		userService.save(username, password);
	}
	
	/**
	 * Loads a {@link spring.model.User} along with {@link spring.proxy.Proxy}s of associated
	 * {@link spring.model.Idea}s and {@link spring.model.Development}s into the session.
	 * 
	 * @param session The HttpSession representing the session.
	 */
	public void loadUser(HttpSession session) {
		User user = userService.loadByUsername(session.getId(), (String) session.getAttribute("username"));
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
	
	/**
	 * Loads a {@link spring.proxy.Proxy} of {@link spring.model.User}s into the session.
	 * 
	 * @param session The HttpSession representing the session.
	 */
	public void loadUsersByKeyword(HttpSession session) {
		Proxy<User> proxy = userService.loadByPage(session.getId(), (String) session.getAttribute("keyword"), (int) session.getAttribute("searchPageOfUsers"));
		session.setAttribute("numUserPages", proxy.getNumPages());
		session.setAttribute("userPage", proxy.getPage());
		session.setAttribute("users", proxy.getPagedData());
	}
	
	// ### Idea ###
	/**
	 * Creates and saves an {@link spring.model.Idea}.
	 * 
	 * @param session The HttpSession representing the session.
	 * @param title The String representing the idea's title.
	 * @param description The String representing the idea's description.
	 * @param username The String representing the associated {@link spring.model.User}'s 
	 * username.
	 */
	public void createIdea(HttpSession session, String title, String description, String username) {
		User user = userService.loadByUsername(session.getId(), username);
		session.setAttribute("user", user);
		Idea idea = ideaService.create(title, description, user);
		user.addIdea(idea);
		userService.saveOrUpdate(user);
		ideaService.add(idea);
	}
	
	/**
	 * Loads an {@link spring.model.Idea} along with {@link spring.proxy.Proxy}s of associated
	 * {@link spring.model.Development}s and {@link spring.model.Comment}s into the session.
	 * 
	 * @param session The HttpSession representing the session.
	 */
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
	
	/**
	 * Loads a {@link spring.proxy.Proxy} of {@link spring.model.Idea}s into the session.
	 * 
	 * @param session The HttpSession representing the session.
	 */
	public void loadIdeasByPage(HttpSession session) {
		Proxy<Idea> proxy = ideaService.loadByPage(session.getId(), (int) session.getAttribute("homePageOfIdeas"));
		session.setAttribute("numHomePagesOfIdeas", proxy.getNumPages());
		session.setAttribute("homePageOfIdeas", proxy.getPage());
		session.setAttribute("homeIdeas", proxy.getPagedData());
	}
	
	// ### Development ###
	/**
	 * Creates and saves a {@link spring.model.Development}. If a {@link spring.model.Development}
	 * with the same associated {@link spring.model.Idea} and {@link spring.model.User} exists,
	 * do nothing.
	 * 
	 * @param session The HttpSession representing the session.
	 * @param link The String representing the session.
	 * @param username The String representing the {@link spring.model.User}'s username.
	 */
	public void createDevelopment(HttpSession session, String link, String username) {
		User user = userService.loadByUsername(session.getId(), username);
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
	/**
	 * Creates and saves a {@link spring.model.Comment}.
	 * 
	 * @param session The HttpSession representing the session.
	 * @param username The String representing the {@link spring.model.User}'s username.
	 * @param comment The String representing the comment.
	 */
	public void createComment(HttpSession session, String username, String comment) {
		User user = userService.loadByUsername(session.getId(), username);
		session.setAttribute("user", user);
		Idea idea = (Idea) session.getAttribute("idea");
		idea = ideaService.loadById(idea.getId());
		session.setAttribute("idea", idea);
		Comment c = commentService.create(user, comment);
		idea.addComment(c);
		ideaService.saveOrUpdate(idea);
	}
	
	// ### IdeaVote ###
	/**
	 * Creates and saves an @{link spring.model.IdeaVote}.
	 * 
	 * @param session The HttpSession representing the session.
	 * @param username The String representing the {@link spring.model.User}'s username.
	 * @param upVote The boolean representing the vote status.
	 * @param ideaId The int representing the {@link spring.model.Idea}'s id.
	 */
	public void createIdeaVote(HttpSession session, String username, Boolean upVote, int ideaId) {
		User user = userService.loadByUsername(session.getId(), username);
		session.setAttribute("user", user);
		Idea idea = (Idea) session.getAttribute("idea");
		idea = ideaService.loadById(idea.getId());
		session.setAttribute("idea", idea);
		IdeaVote vote = voteService.create(user, upVote, idea);
		idea.addVote(vote);
		ideaService.saveOrUpdate(idea);
	}
	
	/**
	 * Creates and saves a @{link spring.model.DevelopmentVote}.
	 * 
	 * @param session The HttpSession representing the session.
	 * @param username The String representing the {@link spring.model.User}'s username.
	 * @param upVote The boolean representing the vote status.
	 * @param developmentId The int representing the {@link spring.model.Development}'s id.
	 */
	// ### DevelopmentVote ###
	public void createDevelopmentVote(HttpSession session, String username, Boolean upVote, int developmentId) {
		User user = userService.loadByUsername(session.getId(), username);
		session.setAttribute("user", user);
		Idea idea = (Idea) session.getAttribute("idea");
		idea = ideaService.loadById(idea.getId());
		session.setAttribute("idea", idea);
		Development development = developmentService.loadById(session.getId(), developmentId);
		DevelopmentVote vote = voteService.create(user, upVote, development);
		development.addVote(vote);
		developmentService.saveOrUpdate(development);
	}
	
	/**
	 * Creates and saves an @{link spring.model.CommentVote}.
	 * 
	 * @param session The HttpSession representing the session.
	 * @param username The String representing the {@link spring.model.User}'s username.
	 * @param upVote The boolean representing the vote status.
	 * @param commentId The int representing the {@link spring.model.Comment}'s id.
	 */
	// ### CommentVote ###
	public void createCommentVote(HttpSession session, String username, Boolean upVote, int commentId) {
		User user = userService.loadByUsername(session.getId(), username);
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
