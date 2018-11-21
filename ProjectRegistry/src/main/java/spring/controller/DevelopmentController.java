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
 * Controller handles all requests for URLs beginning with "/development".
 * This controller is designed for the creation and manipulation of 
 * {@link spring.model.Development} objects.
 * 
 * @author Shane Lockwood
 *
 */
@Controller
public class DevelopmentController extends AbstractController {

	/**
	 * Loads the form for creating a new {@link spring.model.Development}.
	 * 
	 * @return model
	 */
	@GetMapping("/development/new/form")
	public ModelAndView postDevelopment() {
		ModelAndView model = new ModelAndView();
		model.setViewName("newDevelopmentForm");
		return model;
	}
	
	/**
	 * Accepts parameters from the new development form to create and save a 
	 * {@link spring.model.Development} object.
	 * 
	 * @param link The String containing the link provided by the client.
	 * @param session The HttpSession passed between the client and server.
	 * @param request The HttpServletRequest which stores the UserPrincipal.
	 * @return model
	 */
	@PostMapping("/development/new/create")
	public RedirectView newDevelopment(@RequestParam(value="link", required=true) String link,
									   HttpSession session,
									   HttpServletRequest request) {
		RedirectView view = new RedirectView();
		serviceFacade.createDevelopment(session, link, request.getUserPrincipal().getName());
		view.setUrl("/idea");
		view.setContextRelative(true);
		return view;
	}
	
	/**
	 * Adds or modifies a {@link spring.model.DevelopmentVote}.
	 * 
	 * @param upVote The String representation of a boolean.
	 * @param developmentId The Integer for the associated {@link spring.model.Development}.
	 * @param session The HttpSession passed between the client and server.
	 * @param request The HttpServletRequest which stores the UserPrincipal.
	 * @return model
	 */
	@PostMapping("/development/new/vote")
	public RedirectView developmentVote(@RequestParam(value="upVote", required=true) String upVote,
								   		@RequestParam(value="developmentId", required=true) String developmentId,
								   		HttpSession session,
								   		HttpServletRequest request) {
		RedirectView view = new RedirectView();
		serviceFacade.createDevelopmentVote(session, request.getUserPrincipal().getName(), Boolean.valueOf(upVote), Integer.parseInt(developmentId));
		view.setUrl("/idea");
		view.setContextRelative(true);
		return view;
	}
}
