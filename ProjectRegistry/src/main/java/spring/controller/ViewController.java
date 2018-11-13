package spring.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import spring.service.*;
import spring.model.*;

@Controller
public class ViewController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private IdeaService ideaService;
	
	@Autowired 
	private DevelopmentService developmentService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private VoteService voteService;
	
	
	@GetMapping({"/", "/home"})
	public ModelAndView home(@RequestParam(value="page", required=false) Integer page,
							 HttpSession session) {
		ModelAndView model = new ModelAndView();
		model.addObject("ideas", ideaService.loadAll(session, page));
		model.setViewName("home");
		return model;
	}
	
	@PostMapping("/home/filter")
	public ModelAndView filterHomeByDate(@RequestParam(value="startDate", required=false) @DateTimeFormat(pattern="yyy-MM-dd") Date startDate,
										 @RequestParam(value="stopDate", required=false) @DateTimeFormat(pattern="yyy-MM-dd") Date stopDate) {
		ModelAndView model = new ModelAndView();
		model.addObject("ideas", ideaService.loadByFilter(startDate, stopDate));
		model.setViewName("home");
		return model;
	}
	
	@GetMapping("/login")
	public ModelAndView login(@RequestParam(value="error", required=false) String error,
							  @RequestParam(value="logout", required=false) String logout) {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and/or password!");
		}
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("/login");
		return model;
	}
	
	@GetMapping("/user/new/form")
	public ModelAndView newUserPage(HttpSession session,
									HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		session.setAttribute("previousView", request.getHeader("Referer"));
		model.addObject("user", userService.create());
		model.setViewName("newUserForm");
		return model;
	}
	
	@PostMapping("/user/new/create")
	public RedirectView newUser(@RequestParam(value="username", required=true) String username,
								@RequestParam(value="password", required=true) String password,
								HttpSession session) {
		RedirectView view = new RedirectView();
		userService.save(username, password);
		view.setUrl((String)session.getAttribute("previousView"));
		view.setContextRelative(true);
		session.removeAttribute("previousView");
		return view;
	}
	
	@GetMapping("/user/search")
	public ModelAndView usersearch(@RequestParam(value="search", required=true) String search) {
		ModelAndView model = new ModelAndView();
		model.addObject("keyword", search);
		model.addObject("users", userService.loadBySearch(search));
		model.setViewName("userSearch");
		return model;
	}
	
	@GetMapping("/user/profile")
	public ModelAndView userProfile(@RequestParam(value="username", required=true) String username) {
		ModelAndView model = new ModelAndView();
		User user = userService.loadByUsername(username);
		model.addObject("user", user);
		model.setViewName("userProfile");
		return model;
	}
	
	@GetMapping("/idea/new/form")
	public ModelAndView postIdea() {
		ModelAndView model = new ModelAndView();
		model.setViewName("newIdeaForm");
		return model;
	}
	
	@PostMapping("/idea/new/create")
	public RedirectView newIdea(@RequestParam(value="title", required=true) String title,
								@RequestParam(value="description", required=true) String description,
								HttpServletRequest request) {
		RedirectView view = new RedirectView();
		User user = userService.loadByUsername(request.getUserPrincipal().getName());
		Idea idea = ideaService.create(title, description, user);
		userService.update(user.addIdea(idea));
		view.setUrl("/home");
		view.setContextRelative(true);
		return view;
	}
	
	@GetMapping("/idea")
	public ModelAndView loadIdeaById(@RequestParam(value="id", required=true) int id,
									 HttpSession session) {
		ModelAndView model = new ModelAndView();
		Idea idea = ideaService.loadById(id);
		model.addObject("idea", idea);
		model.setViewName("idea");
		session.setAttribute("ideaId", id);
		return model;
	}
	
	@PostMapping("/idea/new/vote")
	public RedirectView ideaVote(@RequestParam(value="upVote", required=true) String upVote,
								   HttpSession session,
							   	   HttpServletRequest request) {
		RedirectView view = new RedirectView();
		User voter = userService.loadByUsername(request.getUserPrincipal().getName());
		Idea idea = ideaService.loadById((int)session.getAttribute("ideaId"));
		IdeaVote vote = voteService.create(voter, Boolean.valueOf(upVote), idea);
		idea.addVote(vote);
		ideaService.update(idea);
		view.setUrl("/idea?id=" + Integer.toString((int)session.getAttribute("ideaId")));
		view.setContextRelative(true);
		session.removeAttribute("ideaId");
		return view;
	}
	
	@GetMapping("/development/new/form")
	public ModelAndView postDevelopment() {
		ModelAndView model = new ModelAndView();
		model.setViewName("newDevelopmentForm");
		return model;
	}
	
	@PostMapping("/development/new/create")
	public RedirectView newDevelopment(@RequestParam(value="link", required=true) String link,
									   HttpSession session,
									   HttpServletRequest request) {
		RedirectView view = new RedirectView();
		User user = userService.loadByUsername(request.getUserPrincipal().getName());
		Idea idea = ideaService.loadById((int)session.getAttribute("ideaId"));
		Development development = developmentService.create(idea, link, user);
		idea.addDevelopment(development);
		user.addDevelopment(development);
		ideaService.update(idea);
		userService.update(user);
		view.setUrl("/idea?id=" + Integer.toString((int)session.getAttribute("ideaId")));
		view.setContextRelative(true);
		session.removeAttribute("ideaId");
		return view;
	}
	
	@PostMapping("/development/new/vote")
	public RedirectView developmentVote(@RequestParam(value="upVote", required=true) String upVote,
								   		@RequestParam(value="developmentId", required=true) String developmentId,
								   		HttpSession session,
								   		HttpServletRequest request) {
		RedirectView view = new RedirectView();
		User voter = userService.loadByUsername(request.getUserPrincipal().getName());
		Development development = developmentService.loadById(Integer.parseInt(developmentId));
		DevelopmentVote vote = voteService.create(voter, Boolean.valueOf(upVote), development);
		development.addVote(vote);
		developmentService.update(development);
		view.setUrl("/idea?id=" + Integer.toString((int)session.getAttribute("ideaId")));
		view.setContextRelative(true);
		session.removeAttribute("ideaId");
		return view;
	}
	
	@GetMapping("/comment/new/form")
	public ModelAndView postComment() {
		ModelAndView model = new ModelAndView();
		model.setViewName("newCommentForm");
		return model;
	}
	
	@PostMapping("/comment/new/create")
	public RedirectView newComment(@RequestParam(value="comment", required=true) String comment,
								   HttpSession session,
								   HttpServletRequest request) {
		RedirectView view = new RedirectView();
		Idea idea = ideaService.loadById((int)session.getAttribute("ideaId"));
		User commenter = userService.loadByUsername(request.getUserPrincipal().getName());
		Comment c = commentService.create(commenter, comment);
		idea.addComment(c);
		ideaService.update(idea);
		view.setUrl("/idea?id=" + Integer.toString((int)session.getAttribute("ideaId")));
		view.setContextRelative(true);
		session.removeAttribute("ideaId");
		return view;
	}
	
	@PostMapping("/comment/new/vote")
	public RedirectView commentVote(@RequestParam(value="upVote", required=true) String upVote,
								   @RequestParam(value="commentId", required=true) String commentId,
								   HttpSession session,
							   	   HttpServletRequest request) {
		RedirectView view = new RedirectView();
		User voter = userService.loadByUsername(request.getUserPrincipal().getName());
		Comment comment = commentService.loadById(Integer.parseInt(commentId));
		CommentVote vote = voteService.create(voter, Boolean.valueOf(upVote), comment);
		comment.addVote(vote);
		commentService.update(comment);
		view.setUrl("/idea?id=" + Integer.toString((int)session.getAttribute("ideaId")));
		view.setContextRelative(true);
		session.removeAttribute("ideaId");
		return view;
	}
}
