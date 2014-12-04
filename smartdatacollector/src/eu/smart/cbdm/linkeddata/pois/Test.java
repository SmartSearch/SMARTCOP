/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.linkeddata.pois;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
public class Test {

    public static void main(String args[]) {
        {

                double lat = 41.933987;
                double lon = 12.454995;

                PointsNearby _points = new PointsNearby(lon, lat);
                
                Document xmlDocument = _points.getXmlDocument();


                NodeList titleNodes = xmlDocument.getElementsByTagName("title");
                NodeList articleURLNodes = xmlDocument.getElementsByTagName("wikipediaUrl");
                NodeList articleRank = xmlDocument.getElementsByTagName("rank");
                NodeList articleLang = xmlDocument.getElementsByTagName("lang");
                NodeList articleFeature = xmlDocument.getElementsByTagName("feature");
                NodeList articleCountryCode = xmlDocument.getElementsByTagName("countryCode");
                NodeList articleDistance = xmlDocument.getElementsByTagName("distance");
                
                for (int i = 0; i < titleNodes.getLength(); i++) {
                    System.out.println("Articolo " + (i + 1) + ": " + titleNodes.item(i).getTextContent() + " "+articleRank.item(i).getTextContent()+ " "+articleFeature.item(i).getTextContent()+ " "+articleDistance.item(i).getTextContent());
                }

        }

    }
}
