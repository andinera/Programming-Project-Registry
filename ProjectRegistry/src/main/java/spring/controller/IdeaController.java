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
							 @RequestParam(value="developmentPage", required=false) Integer developmentPage,
							 @RequestParam(value="commentPage", required=false) Integer commentPage,
							 HttpSession session) {
		ModelAndView model = new ModelAndView();
		model.setViewName("idea");
		if (id == null) {
			id = (Integer) session.getAttribute("ideaId");
		} else {
			session.setAttribute("ideaId", id);
		}
		if (developmentPage == null) {
			if (session.getAttribute("developmentPage") == null) {
				developmentPage = 1;
			} else {
				developmentPage = (Integer) session.getAttribute("developmentPage");
			}
		}
		if (commentPage == null) {
			if (session.getAttribute("commentPage") == null) {
				commentPage = 1;
			} else {
				commentPage = (Integer) session.getAttribute("commentPage");
			}
		}
		serviceFacade.loadIdea(session, id, developmentPage, commentPage);
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
