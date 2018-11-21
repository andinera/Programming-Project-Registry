package spring.controller;

import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;



/**
 * This Controller handles all requests for URLs beginning with "/comment".
 * This controller is designed for the creation and manipulation of {@link spring.model.Comment} 
 * objects.
 * 
 * @author Shane Lockwood
 *
 */
@Controller
public class CommentController extends AbstractController {
	
	/**
	 * Loads the form for creating a new {@link spring.model.Comment}.
	 * 
	 * @return model
	 */
	@GetMapping("/comment/new/form")
	public ModelAndView postComment() {
		ModelAndView model = new ModelAndView();
		model.setViewName("newCommentForm");
		return model;
	}
	
	/**
	 * Accepts parameters from the new comment form to create and save a 
	 * {@link spring.model.Comment} object.
	 * 
	 * @param comment The String containing the comment provided by the client.
	 * @param session The HttpSession passed between the client and server.
	 * @param request The HttpServletRequest which stores the UserPrincipal.
	 * @return model
	 */
	@PostMapping("/comment/new/create")
	public RedirectView newComment(@RequestParam(value="comment", required=true) String comment,
								   HttpSession session,
								   HttpServletRequest request) {
		RedirectView view = new RedirectView();
		serviceFacade.createComment(session, request.getUserPrincipal().getName(), comment);
		view.setUrl("/idea");
		view.setContextRelative(true);
		return view;
	}
	
	/**
	 * Adds or modifies a {@link spring.model.CommentVote}.
	 * 
	 * @param upVote The String representation of a boolean.
	 * @param commentId The Integer for the associated {@link spring.model.Comment}.
	 * @param session The HttpSession passed between the client and server.
	 * @param request The HttpServletRequest which stores the UserPrincipal.
	 * @return model
	 */
	@PostMapping("/comment/new/vote")
	public RedirectView commentVote(@RequestParam(value="upVote", required=true) String upVote,
								    @RequestParam(value="commentId", required=true) Integer commentId,
								    HttpSession session,
							   	    HttpServletRequest request) {
		RedirectView view = new RedirectView();
		serviceFacade.createCommentVote(session, request.getUserPrincipal().getName(), Boolean.valueOf(upVote), commentId);
		view.setUrl("/idea");
		view.setContextRelative(true);
		return view;
	}
}
