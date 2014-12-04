/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.datafusion;

import eu.smart.cbdm.logs.LogManager;
import eu.smart.cbdm.logs.LogMessage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author albert
 */
public class DataFusionContext implements ServletContextListener {
     private ExecutorService executor;
     private DataFusionHandler datafusion = null;

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        int nr_executors = -1;
        //ThreadFactory daemonFactory = new DaemonThreadFactory();
        executor = Executors.newSingleThreadExecutor();
        context.setAttribute("DATAFUSIO_EXECUTOR", executor);
        
        LogManager _logger = LogManager.getInstance();
        context.setAttribute("LOGGER", _logger);
        LogMessage _log = new LogMessage();
        _log.severity = LogMessage.SEVERITY_INFO;
        _log.message = "Log Manager Started";
        _log.description = "Log manager is correctly started";
        _logger.addLogMessage(_log);
        
        Logger.getLogger(DataFusionContext.class.getName()).log(Level.INFO, "DataFusionContext Starting init sequence");
        long wait = 30000;
        datafusion = new DataFusionHandler();
        ExecutorService executor = (ExecutorService) context.getAttribute("DATAFUSIO_EXECUTOR");
        Thread t = new Thread(datafusion);
        t.setName("DatafusionHandler");
        executor.execute(t);
        Logger.getLogger(DataFusionContext.class.getName()).log(Level.INFO, "Init sequence ok");
        
        

    }

    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        datafusion.setActive(false);
        executor.shutdown(); // Disable new tasks from being submitted
        executor.shutdownNow(); 
  


    }
    
}
