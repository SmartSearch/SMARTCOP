package org.smart.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
 
/**
 * Controller for the index and logout pages
 * @author Wladimir Totino
 *
 */
@Controller
public class IndexController extends BaseController {
 
	/**
	 * Opens the index page
	 * @param request
	 * @param map
	 * @return
	 */
    @RequestMapping("/index.htm")
    public String index(HttpServletRequest request, Map<String, Object> map) {
        return "index";
    }
    
    /**
     * redirects the browser to the logout page. Spring Security will handle the logout
     * @return
     */
    @RequestMapping("logout.htm")
    public String logout() {
    	return "redirect:/index.htm";
    }
}