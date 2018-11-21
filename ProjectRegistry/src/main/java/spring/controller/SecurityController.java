package spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * Controller handles all security related requests.
 * 
 * @author Shane Lockwood
 *
 */
@Controller
public class SecurityController extends AbstractController {

	/**
	 * Handles user logins.
	 * 
	 * @param error The String representing the client provided an invalid username/password.
	 * @param logout The String representing the client wishes to logout.
	 * @return model
	 */
	@GetMapping("/login")
	public ModelAndView login(@RequestParam(value="error", required=false) String error,
							  @RequestParam(value="logout", required=false) String logout) {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and/or password!");
		}
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("/login");
		return model;
	}
}
