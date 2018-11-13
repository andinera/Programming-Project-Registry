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

import spring.service.ServiceFacade;

@Controller
public class ViewController {
	
	@Autowired
	private ServiceFacade serviceFacade;
	

	@GetMapping({"/", "/home"})
	public ModelAndView home(HttpSession session) {
		ModelAndView model = new ModelAndView();
		model.addObject("ideas", serviceFacade.loadIdeas(session));
		model.setViewName("home");
		return model;
	}
//	
////	@GetMapping("/home/filter")
////	public ModelAndView filterHomeByDate(@RequestParam(value="startDate", required=false) @DateTimeFormat(pattern="yyy-MM-dd") Date startDate,
////										 @RequestParam(value="stopDate", required=false) @DateTimeFormat(pattern="yyy-MM-dd") Date stopDate) {
////		ModelAndView model = new ModelAndView();
////		model.addObject("ideas", ideaService.loadByFilter(startDate, stopDate));
////		model.setViewName("home");
////		return model;
////	}
//	
	@GetMapping("/home/proxy")
	public ModelAndView homeProxy(@RequestParam(value="homePage", required=true) Integer homePage,
			 					  HttpSession session) {
		ModelAndView model = new ModelAndView();
		model.addObject("ideas", serviceFacade.loadIdeasByProxy(session, homePage));
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
		model.addObject("user", serviceFacade.createUser());
		model.setViewName("newUserForm");
		return model;
	}
	
	@PostMapping("/user/new/create")
	public RedirectView newUser(@RequestParam(value="username", required=true) String username,
								@RequestParam(value="password", required=true) String password,
								HttpSession session) {
		RedirectView view = new RedirectView();
		serviceFacade.saveUser(username, password);
		view.setUrl((String)session.getAttribute("previousView"));
		view.setContextRelative(true);
		return view;
	}
	
	@GetMapping("/user/search")
	public ModelAndView usersearch(@RequestParam(value="search", required=true) String search) {
		ModelAndView model = new ModelAndView();
		model.addObject("keyword", search);
		model.addObject("users", serviceFacade.loadUsersBySearch(search));
		model.setViewName("userSearch");
		return model;
	}
	
	@GetMapping("/user/profile")
	public ModelAndView userProfile(@RequestParam(value="username", required=true) String username) {
		ModelAndView model = new ModelAndView();
		model.addObject("user", serviceFacade.loadUser(username));
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
		serviceFacade.createIdea(title, description, request.getUserPrincipal().getName());
		view.setUrl("/home");
		view.setContextRelative(true);
		return view;
	}
	
	@GetMapping("/idea")
	public ModelAndView idea(@RequestParam(value="id", required=true) int id,
							 HttpSession session) {
		ModelAndView model = new ModelAndView();
		model.setViewName("idea");
		session.setAttribute("ideaId", id);
		session.setAttribute("idea", serviceFacade.loadIdea(session, id));
		return model;
	}
	
	@GetMapping("/idea/proxy/development")
	public ModelAndView ideaProxyDevelopment(@RequestParam(value="developmentPage", required=true) Integer developmentPage,
			 								 HttpSession session) {
		ModelAndView model = new ModelAndView();
		model.setViewName("idea");
		serviceFacade.loadIdeaByDevelopmentProxy(session, developmentPage);
		return model;
	}
	
	@GetMapping("/idea/proxy/comment")
	public ModelAndView ideaProxyComment(@RequestParam(value="commentPage", required=true) Integer commentPage,
			 							 HttpSession session) {
		ModelAndView model = new ModelAndView();
		model.setViewName("idea");
		serviceFacade.loadIdeaByCommentProxy(session, commentPage);
		return model;
	}
	
	@PostMapping("/idea/new/vote")
	public RedirectView ideaVote(@RequestParam(value="upVote", required=true) String upVote,
								   HttpSession session,
							   	   HttpServletRequest request) {
		RedirectView view = new RedirectView();
		serviceFacade.createIdeaVote(request.getUserPrincipal().getName(), Boolean.valueOf(upVote), (int)session.getAttribute("ideaId"));
		view.setUrl("/idea?id=" + Integer.toString((int)session.getAttribute("ideaId")));
		view.setContextRelative(true);
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
		serviceFacade.createDevelopment((int)session.getAttribute("ideaId"), link, request.getUserPrincipal().getName());
		view.setUrl("/idea?id=" + Integer.toString((int)session.getAttribute("ideaId")));
		view.setContextRelative(true);
		return view;
	}
	
	@PostMapping("/development/new/vote")
	public RedirectView developmentVote(@RequestParam(value="upVote", required=true) String upVote,
								   		@RequestParam(value="developmentId", required=true) String developmentId,
								   		HttpSession session,
								   		HttpServletRequest request) {
		RedirectView view = new RedirectView();
		serviceFacade.createDevelopmentVote(request.getUserPrincipal().getName(), Boolean.valueOf(upVote), Integer.parseInt(developmentId));
		view.setUrl("/idea?id=" + Integer.toString((int)session.getAttribute("ideaId")));
		view.setContextRelative(true);
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
		serviceFacade.createComment((int)session.getAttribute("ideaId"), request.getUserPrincipal().getName(), comment);
		view.setUrl("/idea?id=" + Integer.toString((int)session.getAttribute("ideaId")));
		view.setContextRelative(true);
		return view;
	}
	
	@PostMapping("/comment/new/vote")
	public RedirectView commentVote(@RequestParam(value="upVote", required=true) String upVote,
								   @RequestParam(value="commentId", required=true) String commentId,
								   HttpSession session,
							   	   HttpServletRequest request) {
		RedirectView view = new RedirectView();
		serviceFacade.createCommentVote(request.getUserPrincipal().getName(), Boolean.valueOf(upVote), Integer.parseInt(commentId));
		view.setUrl("/idea?id=" + Integer.toString((int)session.getAttribute("ideaId")));
		view.setContextRelative(true);
		return view;
	}
}
