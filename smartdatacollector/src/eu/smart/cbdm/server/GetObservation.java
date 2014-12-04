/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.server;

import eu.smart.cbdm.datafusion.Observation;
import eu.smart.cbdm.logs.LogManager;
import eu.smart.cbdm.logs.LogMessage;
import eu.smart.cbdm.messages.GetObservationMessage;
import eu.smart.cbdm.messages.SystemLogsMessage;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
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
public class GetObservation extends SMARTServlet {

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
        String loc_id = request.getParameter("loc_id");
        GetObservationMessage _msg = new GetObservationMessage();

        try {
            
            _msg.addObservation(this.getLastObservation(loc_id));
            
            
        } finally {
            out.print(_msg.toJSON(0));
            out.close();
        }

    }

    private Observation getLastObservation(String id_location) {
        Observation _obs = null;

        String query = "SELECT loc_id, crowd_score, plause_score, activity, sound_level, id_oservation, "
                + "traffic_score, music_score, crowd_density, media, temperature, behaviour "
                + "FROM observations where loc_id=? order by activity desc LIMIT 1";


        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {

            conn = this.getDatasource();
            stat = conn.prepareStatement(query);
            stat.setString(1, id_location);
            rs = stat.executeQuery();

            if (rs.next()) {
                _obs = new Observation();
                _obs.id_location = rs.getString(1);
                _obs.crowd_score = rs.getDouble(2);
                _obs.plause_score = rs.getDouble(3);
                _obs.activity = rs.getTimestamp(4);
                _obs.sound_level = rs.getDouble(5);
                _obs.id_observation = rs.getString(6);
                _obs.traffic_score = rs.getDouble(7);
                _obs.music_score = rs.getDouble(8);
                _obs.crowd_density = rs.getDouble(9);
                _obs.multimedia = rs.getString(10);
                _obs.temperature = rs.getDouble(11);
                _obs.behaviour = rs.getString(12);


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

        }

        return _obs;

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
