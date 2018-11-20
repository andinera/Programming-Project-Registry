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
public class DevelopmentController extends AbstractController {

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
		serviceFacade.createDevelopment(session, link, request.getUserPrincipal().getName());
		view.setUrl("/idea");
		view.setContextRelative(true);
		return view;
	}
	
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
