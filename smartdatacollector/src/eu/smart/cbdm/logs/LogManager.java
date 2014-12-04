/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.logs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author albert
 */
public class LogManager {
    
    private static LogManager instance = null;
    public static LogManager getInstance(){
        if(instance==null) instance = new LogManager();
        return instance;
    }
            
            

    private List<LogMessage> point_by_datetime = null;

    private LogManager() {

        point_by_datetime = new ArrayList();
    }

    public void addLogMessage(LogMessage _log) {
        point_by_datetime.add(_log);
        if(point_by_datetime.size()>10) point_by_datetime.remove(0);
        Collections.sort(point_by_datetime);
    }

    public LogMessage getLastMessage() {

        return point_by_datetime.get(0);

    }

    public Iterator<LogMessage> getLastMessages() {
        
        List<LogMessage> last_point_by_datetime = new ArrayList();
        
        Iterator<LogMessage> _iter = point_by_datetime.iterator();
        
        int numresult = 0;
        while(_iter.hasNext()){
            if(numresult==10) break;
            numresult++;
            last_point_by_datetime.add(_iter.next());
        }
        
        point_by_datetime = last_point_by_datetime;
        Collections.sort(point_by_datetime);
                
        return last_point_by_datetime.iterator();

    }
}
