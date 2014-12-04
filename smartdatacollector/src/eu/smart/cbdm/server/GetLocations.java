/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.server;

import eu.smart.cbdm.datafusion.Location;
import eu.smart.cbdm.messages.LocationsMessage;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author albert
 */
public class GetLocations extends SMARTServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = response.getWriter();
        LocationsMessage _msg = new LocationsMessage();
        String query_locations = "SELECT loc_id, latitude, longitude, loc_name, loc_address FROM locations";
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {

            conn = this.getDatasource();
            stat = conn.prepareStatement(query_locations);
            rs = stat.executeQuery();

            while (rs.next()) {
                Location _loc = new Location();
                _loc.id = rs.getString(1);
                _loc.latitude = rs.getDouble(2);
                _loc.longitude = rs.getDouble(3);
                _loc.name = rs.getString(4);
                _loc.address = rs.getString(5);
                _msg.addLocation(_loc);

            }

            rs.close();
            rs = null;
            stat.close();
            stat = null;
            conn.close();
            conn = null;

        } catch (SQLException ex) {
            Logger.getLogger(GetLocations.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    Logger.getLogger(GetLocations.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (stat != null) {
                try {
                    stat.close();
                    stat = null;
                } catch (SQLException ex) {
                    Logger.getLogger(GetLocations.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                } catch (SQLException ex) {
                    Logger.getLogger(GetLocations.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            out.print(_msg.toJSON(0));
            out.close();
        }



    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
