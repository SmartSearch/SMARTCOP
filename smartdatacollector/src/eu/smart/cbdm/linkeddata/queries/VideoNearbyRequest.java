/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.linkeddata.queries;

/**
 *
 * @author albert
 */
public class VideoNearbyRequest {
        //public final static int XML_FORMATTED = 0x01;
    //public final static int JSON_FORMATTED = 0x02;
    public static String QUERY_LOC_BY_POSITION_XML = "http://api.geonames.org/findNearbyWikipedia?";
    public double latitude = 0.0;
    public double longitude = 0.0;
    public String user = "smartfp7user";
    public double radius = 0.5;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRequestURI() {

        String uri = PointsNearbyRequest.QUERY_LOC_BY_POSITION_XML;

        uri = uri + "lat=" + this.getLatitude();
        uri = uri + "&lng=" + this.getLongitude();
        uri = uri + "&username=" + this.getUser();
        uri = uri + "&radius=" + this.getRadius();
        return uri;
    }

    @Override
    public String toString() {
        return this.getRequestURI();
    }
    
}
