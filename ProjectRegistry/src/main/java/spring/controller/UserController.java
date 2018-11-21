package spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


/**
 * Controller handles all requests for URLs beginning with "/user".
 * This controller is designed for the creation and manipulation of {@link spring.model.User} 
 * objects.
 * 
 * @author Shane Lockwood
 *
 */
@Controller
public class UserController extends AbstractController {

	/**
	 * Loads the form for creating a new {@link spring.model.User}.
	 * 
	 * @param session The HttpSession passed between the client and server.
	 * @param request The HttpServletRequest which stores the UserPrincipal.
	 * @return model
	 */
	@GetMapping("/user/new/form")
	public ModelAndView newUserPage(HttpSession session,
									HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		session.setAttribute("previousView", request.getHeader("Referer"));
		model.setViewName("newUserForm");
		return model;
	}
	
	/**
	 * Accepts parameters from the new user form to create and save a {@link spring.model.User} 
	 * object.
	 * 
	 * @param username The String containing the username provided by the client.
	 * @param password The String containing the password provided by the client.
	 * @param session The HttpSession passed between the client and server.
	 * @return model
	 */
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
	
	/**
	 * Loads the user profile view provided the {@link spring.model.User}'s username with a 
	 * subset of {@link spring.model.Idea}s and {@link spring.model.Development}s.
	 * 
	 * @param username The String containing the username provided by the client.
	 * @param userPageOfIdeas The Integer representing the desired subset of 
	 * {@link spring.model.Idea}s to display.
	 * @param userPageOfDevelopments The Integer representing the desired subset of 
	 * {@link spring.model.Development}s to display.
	 * @param session The HttpSession passed between the client and server.
	 * @return model
	 */
	@GetMapping("/user/profile")
	public ModelAndView userProfile(@RequestParam(value="username", required=false) String username,
									@RequestParam(value="userPageOfIdeas", required=false) Integer userPageOfIdeas,
									@RequestParam(value="userPageOfDevelopments", required=false) Integer userPageOfDevelopments,
									HttpSession session) {
		ModelAndView model = new ModelAndView();
		if (username != null) {
			session.setAttribute("username", username);
		}
		if (userPageOfIdeas == null) {
			if (session.getAttribute("userPageOfIdeas") == null) {
				session.setAttribute("userPageOfIdeas", 1);
			} 
		} else {
			session.setAttribute("userPageOfIdeas", userPageOfIdeas);
		}
		if (userPageOfDevelopments == null) {
			if (session.getAttribute("userPageOfDevelopments") == null) {
				session.setAttribute("userPageOfDevelopments", 1);
			}
		} else {
			session.setAttribute("userPageOfDevelopments", userPageOfDevelopments);
		}
		serviceFacade.loadUser(session);
		model.setViewName("userProfile");
		return model;
	}
	
	/**
	 * Loads the user search view with a subset of all {@link spring.model.User}s with a username 
	 * that contains the passed keyword.
	 * 
	 * @param keyword The String containing the keyword provided by the client.
	 * @param searchPageOfUsers The Integer representing the desired subset of 
	 * {@link spring.model.User}s to display.
	 * @param session The HttpSession passed between the client and server.
	 * @return model
	 */
	@GetMapping("/user/search")
	public ModelAndView userSearch(@RequestParam(value="keyword", required=false) String keyword,
								   @RequestParam(value="searchPageOfUsers", required=false) Integer searchPageOfUsers,
								   HttpSession session) {
		ModelAndView model = new ModelAndView();
		if (keyword != null) {
			session.setAttribute("keyword", keyword);
		}
		if (searchPageOfUsers == null) {
			if (session.getAttribute("searchPageOfUsers") == null) {
				session.setAttribute("searchPageOfUsers", 1);
			} 
		} else {
			session.setAttribute("searchPageOfUsers", searchPageOfUsers);
		}
		serviceFacade.loadUsersByKeyword(session);
		model.setViewName("userSearch");
		return model;
	}
}
