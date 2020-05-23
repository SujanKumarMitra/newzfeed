package com.herokuapp.newsfeedapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.herokuapp.newsfeedapp.entity.UserDetailsImpl;

/**
 * This class is mapped for login url
 * 
 * @since 2020-05-15
 * @author Sujan Kumar Mitra
 */
@Controller
public class LoginController {

	/**
	 * This method return login page to the user if user is logged in. If user is
	 * not logged in, then they are forwarded back to headlines page.
	 * 
	 * @param user    if user is logged in then, instance of UserDetails otherwise
	 *                <strong>null</strong> <strong>(populated by Spring
	 *                Container)</strong>
	 * @param request HTTP request information <strong>(populated by Spring
	 *                Container)</strong>
	 * @return view page of login form
	 * @since 2020-05-15
	 * @author Sujan Kumar Mitra
	 */
	@GetMapping("/login")
	public String getloginForm(@AuthenticationPrincipal UserDetailsImpl user, HttpServletRequest request) {

		if (user != null) {
			/* If user is already logged in, then send them to headlines page with a flag */
			request.setAttribute("isLoggedIn", true);
			return "forward:/";
		}
		return "login";
	}

}
