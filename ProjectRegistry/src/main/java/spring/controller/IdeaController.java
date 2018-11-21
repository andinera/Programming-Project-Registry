package spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class IdeaController extends AbstractController {

	@GetMapping("/idea/new/form")
	public ModelAndView postIdea() {
		ModelAndView model = new ModelAndView();
		model.setViewName("newIdeaForm");
		return model;
	}
	
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
