/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.linkeddata.pois;

import eu.smart.cbdm.linkeddata.queries.PointsNearbyRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author albert
 */
public class PointsNearby {

    private double longitude = 0.0;
    private double latitude = 0.0;
    private Document xmlDocument = null;
    private List<POIComparableByRank> point_by_rank = null;
    private List<POIComparableByDistance> point_by_distance = null;

    public PointsNearby(double _longitude, double _latitude) {
        this.longitude = _longitude;
        this.latitude = _latitude;
        xmlDocument = submit();
    }

    private Document submit() {
        Document xmlDocument = null;
        try {

            PointsNearbyRequest _loc = new PointsNearbyRequest();
            _loc.setLatitude(latitude);
            _loc.setLongitude(longitude);
            String uri = _loc.getRequestURI();
            System.out.println(uri);
            URL _url = new URL(uri);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream stream = _url.openStream();
            xmlDocument = builder.parse(stream);

            NodeList titleNodes = xmlDocument.getElementsByTagName("title");
            double distances[] = new double[titleNodes.getLength()];
            int ranks[] = new int[titleNodes.getLength()];

            //Parse data

            NodeList articleURLNodes = xmlDocument.getElementsByTagName("wikipediaUrl");
            NodeList articleRank = xmlDocument.getElementsByTagName("rank");
            NodeList articleFeature = xmlDocument.getElementsByTagName("feature");
            NodeList articleDistance = xmlDocument.getElementsByTagName("distance");
            NodeList articleSummary = xmlDocument.getElementsByTagName("summary");
            NodeList articleCountryCode = xmlDocument.getElementsByTagName("countryCode");

            this.point_by_rank = new ArrayList();
            this.point_by_distance = new ArrayList();

            for (int i = 0; i < titleNodes.getLength(); i++) {
                POIComparableByDistance _poi = new POIComparableByDistance();
                POIComparableByRank _ranked_poi = new POIComparableByRank();
                _poi.setTitle(titleNodes.item(i).getTextContent());
                _poi.setDistance(Double.parseDouble(articleDistance.item(i).getTextContent()));
                _poi.setFeature(articleFeature.item(i).getTextContent());
                _poi.setRank(Integer.parseInt(articleRank.item(i).getTextContent()));
                _poi.setDescription(articleSummary.item(i).getTextContent());
                _poi.setCountryCode(articleCountryCode.item(i).getTextContent());
                _ranked_poi.clone(_poi);
                point_by_rank.add(_ranked_poi);
                point_by_distance.add(_poi);

            }

            Collections.sort(point_by_rank);
            Collections.sort(point_by_distance);


        } catch (ParserConfigurationException ex) {
            Logger.getLogger(PointsNearby.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(PointsNearby.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PointsNearby.class.getName()).log(Level.SEVERE, null, ex);
        }

        return xmlDocument;

    }

    public Document getXmlDocument() {
        return xmlDocument;
    }

    public Iterator<POIComparableByRank> iteratorByRank() {
        return this.point_by_rank.iterator();
    }
    
    public Iterator<POIComparableByDistance> iteratorByDistance() {
        return this.point_by_distance.iterator();
    }
}
