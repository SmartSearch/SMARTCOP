/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.linkeddata.pois;

/**
 * This class define a Point Of Interest
 * @author albert
 */
public class PointOfInterest  {

    private String title = null;
    private String wikipediaUrl = null;
    private int rank = 0;
    private double distance = 0.0;
    private String feature = null;
    private String description = null;
    private String countryCode = null;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWikipediaUrl() {
        return wikipediaUrl;
    }

    public void setWikipediaUrl(String wikipediaUrl) {
        this.wikipediaUrl = wikipediaUrl;
    }

    public void clone(PointOfInterest p){
        this.distance = p.getDistance();
        this.feature  = p.getFeature();
        this.rank = p.getRank();
        this.title = p.getTitle();
        this.wikipediaUrl = p.getWikipediaUrl();
        this.description = p.getDescription();
    
    }

}
