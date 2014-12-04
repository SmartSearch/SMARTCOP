package eu.smart.cdbm.dao;

/**
 * Wrapper delle eccezioni verificatesi a livello di database
 * 
 * @author Roberto Di Vincenzo
 * 
 */
public class DBException extends Exception {

	private static final long serialVersionUID = -2564425171991690336L;

	/**
	 * 
	 */
	public DBException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public DBException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DBException(Throwable cause) {
		super(cause);
	}

}
