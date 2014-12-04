/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.catalina.core.StandardWrapper;
import org.apache.catalina.core.StandardWrapperFacade;

/**
 *
 * @author albert
 */
public class SMARTServlet extends HttpServlet {
    
    
    private String free_conn_query = "SELECT pid, pg_terminate_backend(pid) FROM pg_stat_activity WHERE pid <> pg_backend_pid() AND usename='vidcast'";

    DataSource ds = null;
    Context ctx = null;

    public Connection getDatasource() {
        String lp_id = null;

        if ((ds == null) && (ctx == null)) {

            try {
                ctx = new InitialContext();
                ds = (DataSource) ctx.lookup("java:comp/env/jdbc/smartdatabase");
            } catch (NamingException ex) {
                Logger.getLogger(SMARTServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Logger.getLogger(KaraServlet.class.getName()).log(Level.INFO, "Context created. Retrieving connection");
        Connection conn = null;
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            Logger.getLogger(SMARTServlet.class.getName()).log(Level.SEVERE, null, e);
        }

        return conn;

    }

    @Override
    public void init() throws ServletException {
        try {
            Field wrappedConfig = StandardWrapperFacade.class.getDeclaredField("config");
            wrappedConfig.setAccessible(true);
            StandardWrapper standardWrapper = (StandardWrapper) wrappedConfig.get(getServletConfig());
            standardWrapper.setMaxInstances(100);
        } catch (Exception e) {
            throw new ServletException("Failed to increment max instances", e);
        }
    }
}
