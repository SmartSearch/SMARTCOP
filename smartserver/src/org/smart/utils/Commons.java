package org.smart.utils;

import org.apache.log4j.Logger;

/**
 * Abstract Class to add common methods and variables to classes that require it. 
 * @author Wladimir Totino
 *
 */
public abstract class Commons {
	/**
	 * Automatically adds a log4j logger to the class that extends this one 
	 */
	protected final Logger log = Logger.getLogger(this.getClass());

}
