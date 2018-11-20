package spring.controller;

import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class CommentController extends AbstractController {
	
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
		serviceFacade.createComment(session, request.getUserPrincipal().getName(), comment);
		view.setUrl("/idea");
		view.setContextRelative(true);
		return view;
	}
	
	@PostMapping("/comment/new/vote")
	public RedirectView commentVote(@RequestParam(value="upVote", required=true) String upVote,
								    @RequestParam(value="commentId", required=true) String commentId,
								    HttpSession session,
							   	    HttpServletRequest request) {
		RedirectView view = new RedirectView();
		serviceFacade.createCommentVote(session, request.getUserPrincipal().getName(), Boolean.valueOf(upVote), Integer.parseInt(commentId));
		view.setUrl("/idea");
		view.setContextRelative(true);
		return view;
	}
}
