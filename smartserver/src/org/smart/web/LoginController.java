package org.smart.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller used for requests related to login
 * @author Wladimir Totino
 *
 */
@Controller
public class LoginController {
	
	@RequestMapping("login.htm")
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public String loginForm() {
    	return "login";
    }
	
	/**
	 * This page is called every 5 minutes to check wheter the user is authenticated or not
	 * @param response
	 * @return
	 */
	@RequestMapping("refresh.htm")
    public String getRefreshPage() {
    	return "misc/empty";
    }
	
	/**
	 * Action called if the user fails to login for some reason
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/loginfailed.htm", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public String loginerror(ModelMap model) {
		model.addAttribute("error", "true");
		return "login";
 
	}
}
