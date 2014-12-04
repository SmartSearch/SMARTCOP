/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.datafusion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author albert
 */
public class TestDateTime {

    public static void main(String args[]) {
        
        String datetime = "2013-11-14T08:47:20.150Z";
        String date = datetime.substring(0, 10);
        String time = datetime.substring(11, 19);
        String timezone = datetime.substring(20,23);
        datetime = date+" "+time+" "+timezone;
        
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // first example
        //2013-11-14T08:47:20.150Z
        try {
            Date d1 = format1.parse( datetime);
            java.sql.Timestamp _time = new java.sql.Timestamp(d1.getTime());
            System.out.print(d1);
        } catch (ParseException ex) {
            Logger.getLogger(TestDateTime.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
}
