package spring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * Controller handles all requests for URLs beginning with "/home".
 * This controller is designed for manipulation of the home page.
 * 
 * @author Shane Lockwood
 *
 */
@Controller
public class HomeController extends AbstractController {

	/**
	 * Loads the home view with a subset of @{link spring.model.Idea}s.
	 * 
	 * @param homePageOfIdeas The Integer representing the desired subset of 
	 * {@link spring.model.Idea}s to display.
	 * @param session The HttpSession passed between the client and server.
	 * @return model
	 */
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
