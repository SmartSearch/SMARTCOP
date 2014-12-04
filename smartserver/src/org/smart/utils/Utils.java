package org.smart.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.smart.exceptions.WebErrorException;

/**
 * Collection of various Utility Methods and Variables
 * @author Wladimir Totino
 *
 */
public class Utils {
	
	public static final String FALSE = "false";
	public static final String TRUE = "true";

	/**
	 * converts a string to a double, if the string actually represents a double
	 * @param str
	 * @return
	 * @throws WebErrorException
	 */
	public static Double strToDouble(String str) throws WebErrorException{
		Double number;
		try{
			number=Double.parseDouble(str);
		} catch (NumberFormatException e){
			throw new WebErrorException("Valore non valido", e);
		}
		return number;
	}
	
	/**
	 * converts a string to a double, if the string actually represents an integer
	 * @param str
	 * @return
	 * @throws WebErrorException
	 */
	public static Integer strToInteger(String str) throws WebErrorException{
		Integer number;
		try{
			number=Integer.parseInt(str);
		} catch (NumberFormatException e){
			throw new WebErrorException("Valore non valido", e);
		}
		return number;
	}
	
	/**
	 * Converts an integer number to a boolean with the following convention: 0 = false, any other number = true
	 * @param number
	 * @return
	 */
	public static boolean intToBool(int number){
		if(number==0){
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Converts a textual representation of a boolean value ("true", "false") to its boolean value
	 * @param str
	 * @return
	 */
	public static boolean strToBool(String str){
		if(str.equalsIgnoreCase(Utils.TRUE)){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Formats a date
	 * @param date
	 * @param international which format to use, wheter to use the international format or the standard format
	 * used in the site
	 * @return
	 */
	public static String formatDate(Date date, boolean international){
		DateFormat dateFormat;
		if(international){
			dateFormat = SimpleDateFormat.getInstance();
		} else {
			dateFormat = new SimpleDateFormat("M-dd-yyyy hh:mm:ss");
		}
		
		return dateFormat.format(date);
	}
	
	/**
	 * Conveniency method to return the current date as a sql.Timestamp object
	 * Mainly used to set dates in model objects
	 * @return
	 */
	public static Timestamp getNow(){
		return new Timestamp(System.currentTimeMillis());
	}
}
