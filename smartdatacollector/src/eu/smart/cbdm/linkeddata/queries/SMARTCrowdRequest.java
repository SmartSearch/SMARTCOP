/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.linkeddata.queries;

/**
 *
 * @author albert
 */
public class SMARTCrowdRequest {

    public static String QUERY_JSON_SMART_CROWD = "http://demos.terrier.org/v1/search.json?q=crowd";
    
    
    
    public String getRequestURI() {

        String uri = SMARTCrowdRequest.QUERY_JSON_SMART_CROWD;
        return uri;
    }

    @Override
    public String toString() {
        return this.getRequestURI();
    }
}
