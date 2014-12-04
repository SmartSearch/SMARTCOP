package org.smart.exceptions;

public class WebErrorException extends Exception {

	/**
	 * Generic Exception to be thrown whenever you want the default error page to be shown
	 * @author Wladimir Totino
	 */
	private static final long serialVersionUID = -6983940326458974926L;

	public WebErrorException() {
		// TODO Auto-generated constructor stub
	}

	public WebErrorException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public WebErrorException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public WebErrorException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
