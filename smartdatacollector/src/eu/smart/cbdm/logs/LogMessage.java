/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.logs;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author albert
 */
public class LogMessage implements Comparable<LogMessage> {
    
    public static String SEVERITY_INFO = "INFO";
    public static String SEVERITY_ALERT = "ALERT";
    public static String SEVERITY_ERROR = "ERROR";
    

    public String severity = null;
    public String datetime = null;
    public String message = null;
    public String description = null;
    public long time_in_mills = 0;

    public LogMessage() {
        time_in_mills = Calendar.getInstance().getTimeInMillis();
        SimpleDateFormat dt = new SimpleDateFormat("[dd-mm-yyyy hh:mm:ss]");
        datetime = dt.format(new Date());
    }
    
    @Override
    public String toString(){
    	
    	return "["+datetime+"]["+severity+"]"+message+"-"+description;
    }

    @Override
    public int compareTo(LogMessage t) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;
        int ret_val = 0;

        if (t.time_in_mills < this.time_in_mills) {
            ret_val = AFTER;
        }
        if (t.time_in_mills > this.time_in_mills) {
            ret_val = BEFORE;
        }


        return ret_val;

    }
}
