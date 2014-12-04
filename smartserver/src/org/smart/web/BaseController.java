package org.smart.web;

import javax.servlet.http.HttpServletRequest;

import org.smart.exceptions.WebErrorException;
import org.smart.utils.Commons;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Class containing methods common to all the controllers
 * @author Wladimir Totino
 *
 */
public class BaseController extends Commons {

	/**
	 * Handles WebErrorExceptions thrown by the classes that extend this one 
	 * by redirecting the browser to an error page where the message
	 * contained in the exception will be showed
	 * @param e
	 * @param request
	 * @return
	 */
    @ExceptionHandler(WebErrorException.class)
    public String handleException(final Exception e, final HttpServletRequest request) {
    	request.setAttribute("exception", e);
        return "error/error";
    }
}