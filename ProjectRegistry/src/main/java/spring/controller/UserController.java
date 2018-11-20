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
public class UserController extends AbstractController {

	@GetMapping("/user/new/form")
	public ModelAndView newUserPage(HttpSession session,
									HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		session.setAttribute("previousView", request.getHeader("Referer"));
		model.setViewName("newUserForm");
		return model;
	}
	
	@PostMapping("/user/new/create")
	public RedirectView newUser(@RequestParam(value="username", required=true) String username,
								@RequestParam(value="password", required=true) String password,
								HttpSession session) {
		RedirectView view = new RedirectView();
		serviceFacade.saveUser(username, password);
		view.setUrl((String)session.getAttribute("previousView"));
		view.setContextRelative(true);
		return view;
	}
	
	@GetMapping("/user/profile")
	public ModelAndView userProfile(@RequestParam(value="username", required=false) String username,
									HttpSession session) {
		ModelAndView model = new ModelAndView();
		serviceFacade.loadUser(session, username);
		model.setViewName("userProfile");
		return model;
	}
	
	@GetMapping("/user/search")
	public ModelAndView userSearch(@RequestParam(value="keyword", required=false) String keyword,
								   @RequestParam(value="userPage", required=false) Integer userPage,
								   HttpSession session) {
		ModelAndView model = new ModelAndView();
		if (keyword == null) {
			keyword = (String) session.getAttribute("keyword");
		}
		if (userPage == null) {
			if (session.getAttribute("userPage") == null) {
				userPage = 1;
			} else {
				userPage = (Integer) session.getAttribute("userPage");
			}
		}
		session.setAttribute("keyword", keyword);
		serviceFacade.loadUsersByKeyword(session, keyword, userPage);
		model.setViewName("userSearch");
		return model;
	}
}
