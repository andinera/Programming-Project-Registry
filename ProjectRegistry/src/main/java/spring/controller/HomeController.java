package spring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController extends AbstractController {
	
	@GetMapping({"/", "/home"})
	public ModelAndView home(@RequestParam(value="homePage", required=false) Integer homePage,
			 				 HttpSession session) {
		ModelAndView model = new ModelAndView();
		if (homePage == null) {
			if (session.getAttribute("homePage") == null) {
				homePage = 1;
			} else {
				homePage = (Integer) session.getAttribute("homePage");
			}
		}
		serviceFacade.loadIdeasByPage(session, homePage);
		model.setViewName("home");
		return model;
	}
}
