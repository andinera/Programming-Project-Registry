package spring.controller;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	@GetMapping({"/", "/home"})
	public ModelAndView home() {
		ModelAndView model = new ModelAndView();
		model.addObject("ideas", ideaService.loadAllIdeas());
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
	public ModelAndView newUserPage() {
		ModelAndView model = new ModelAndView();
		model.addObject("user", userService.buildUser());
		model.setViewName("newUserForm");
		return model;
	}
	
	@PostMapping("/user/new/create")
	public RedirectView newUser(@RequestParam(value="username", required=true) String username,
								@RequestParam(value="password", required=true) String password) {
		RedirectView view = new RedirectView();
		userService.saveUser(username, password);
		view.setUrl("/home");
		view.setContextRelative(true);
		return view;
	}
	
	@GetMapping("/user/search")
	public ModelAndView usersearch(@RequestParam(value="search", required=true) String search) {
		ModelAndView model = new ModelAndView();
		model.addObject("users", userService.loadUsersBySearch(search));
		model.setViewName("userSearch");
		return model;
	}
	
	@GetMapping("/user/profile")
	public ModelAndView userProfile(@RequestParam(value="username", required=true) String username) {
		ModelAndView model = new ModelAndView();
		User user = userService.loadUserByUsername(username);
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
		User user = userService.loadUserByUsername(request.getUserPrincipal().getName());
		Idea idea = ideaService.buildIdea(title, description, user);
		userService.updateUser(user.addIdea(idea));
		view.setUrl("/home");
		view.setContextRelative(true);
		return view;
	}
	
	@GetMapping("/idea")
	public ModelAndView loadIdeaById(@RequestParam(value="id", required=true) int id,
									 HttpSession session) {
		ModelAndView model = new ModelAndView();
		Idea idea = ideaService.loadIdeaById(id);
		model.addObject("idea", idea);
		model.setViewName("idea");
		session.setAttribute("ideaId", id);
		return model;
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
		User user = userService.loadUserByUsername(request.getUserPrincipal().getName());
		Idea idea = ideaService.loadIdeaById((int)session.getAttribute("ideaId"));
		Development development = developmentService.buildDevelopment(idea, link, user);
		idea.addDevelopment(development);
		user.addDevelopment(development);
		ideaService.updateIdea(idea);
		userService.updateUser(user);
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
		Idea idea = ideaService.loadIdeaById((int)session.getAttribute("ideaId"));
		User commenter = userService.loadUserByUsername(request.getUserPrincipal().getName());
		Comment c = commentService.buildComment(commenter, comment);
		idea.addComment(c);
		
//		commentService.saveComment(c);
//		idea.addComment(c);
		ideaService.updateIdea(idea);
		view.setUrl("/idea?id=" + Integer.toString((int)session.getAttribute("ideaId")));
		view.setContextRelative(true);
		session.removeAttribute("ideaId");
		return view;
	}
}
