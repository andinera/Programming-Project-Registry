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
 * Controller handles all requests for URLs beginning with "/idea".
 * This controller is designed for the creation and manipulation of @{link spring.model.Idea} 
 * objects.
 * 
 * @author Shane Lockwood
 *
 */
@Controller
public class IdeaController extends AbstractController {

	/**
	 * Loads the form for creating a new {@link spring.model.Idea}.
	 * 
	 * @return model
	 */
	@GetMapping("/idea/new/form")
	public ModelAndView postIdea() {
		ModelAndView model = new ModelAndView();
		model.setViewName("newIdeaForm");
		return model;
	}
	
	/**
	 * Accepts parameters from the new idea form to create and save an {@link spring.model.Idea}
	 * object.
	 * 
	 * @param title The String containing the title provided by the client.
	 * @param description The String containing the description provided by the client.
	 * @param session The HttpSession passed between the client and server.
	 * @param request The HttpServletRequest which stores the UserPrincipal.
	 * @return model
	 */
	@PostMapping("/idea/new/create")
	public RedirectView newIdea(@RequestParam(value="title", required=true) String title,
								@RequestParam(value="description", required=true) String description,
								HttpServletRequest request,
								HttpSession session) {
		RedirectView view = new RedirectView();
		serviceFacade.createIdea(session, title, description, request.getUserPrincipal().getName());
		view.setUrl("/home");
		view.setContextRelative(true);
		return view;
	}
	
	/**
	 * Loads the idea view provided the @{link spring.model.Idea}'s id with a subset of 
	 * {@link spring.model.Development}s and {@link spring.model.Comment}s.
	 * 
	 * @param id The Integer of the associated Idea's id.
	 * @param ideaPageOfDevelopments The Integer representing the desired subset of 
	 * {@link spring.model.Development}s to display.
	 * @param ideaPageOfComments The Integer representing the desired subset of 
	 * {@link spring.model.Comment}s to display.
	 * @param session The HttpSession passed between the client and server.
	 * @return model
	 */
	@GetMapping("/idea")
	public ModelAndView idea(@RequestParam(value="id", required=false) Integer id,
							 @RequestParam(value="ideaPageOfDevelopments", required=false) Integer ideaPageOfDevelopments,
							 @RequestParam(value="ideaPageOfComments", required=false) Integer ideaPageOfComments,
							 HttpSession session) {
		ModelAndView model = new ModelAndView();
		model.setViewName("idea");
		if (id != null) {
			session.setAttribute("ideaId", id);
		}
		if (ideaPageOfDevelopments == null) {
			if (session.getAttribute("ideaPageOfDevelopments") == null) {
				session.setAttribute("ideaPageOfDevelopments", 1);
			}
		} else {
			session.setAttribute("ideaPageOfDevelopments", ideaPageOfDevelopments);
		}
		if (ideaPageOfComments == null) {
			if (session.getAttribute("ideaPageOfComments") == null) {
				session.setAttribute("ideaPageOfComments", 1);
			}
		} else {
			session.setAttribute("ideaPageOfComments", ideaPageOfComments);
		}
		serviceFacade.loadIdea(session);
		return model;
	}
	
	/**
	 * Adds or modifies an {@link spring.model.IdeaVote}.
	 * 
	 * @param upVote The String representation of a boolean.
	 * @param session The HttpSession passed between the client and server.
	 * @param request The HttpServletRequest which stores the UserPrincipal.
	 * @return model
	 */
	@PostMapping("/idea/new/vote")
	public RedirectView ideaVote(@RequestParam(value="upVote", required=true) String upVote,
								   HttpSession session,
							   	   HttpServletRequest request) {
		RedirectView view = new RedirectView();
		serviceFacade.createIdeaVote(session, request.getUserPrincipal().getName(), Boolean.valueOf(upVote), (int)session.getAttribute("ideaId"));
		view.setUrl("/idea");
		view.setContextRelative(true);
		return view;
	}
}
