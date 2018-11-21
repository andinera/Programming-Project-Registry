package spring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController extends AbstractController {
	
	@GetMapping({"/", "/home"})
	public ModelAndView home(@RequestParam(value="homePageOfIdeas", required=false) Integer homePageOfIdeas,
			 				 HttpSession session) {
		ModelAndView model = new ModelAndView();
		if (homePageOfIdeas == null) {
			if (session.getAttribute("homePageOfIdeas") == null) {
				session.setAttribute("homePageOfIdeas", 1);
			}
		} else {
			session.setAttribute("homePageOfIdeas", homePageOfIdeas);
		}
		serviceFacade.loadIdeasByPage(session);
		model.setViewName("home");
		return model;
	}
}
