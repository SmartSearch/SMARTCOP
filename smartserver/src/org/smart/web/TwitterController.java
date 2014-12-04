package org.smart.web;

import javax.servlet.ServletException;
import org.smart.service.JsonManager;
import org.smart.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
/**
 * Controller used to handle REST requests to the Social Network Manager to retrieve tweets from a certain area
 * @author Wladimir Totino
 *
 */
@Controller
@RequestMapping(value = "/twitter")
public class TwitterController extends BaseController {
	/*Url of the social network manager
	 * TODO: move it in a more configurable place
	 */
	public static final String SOCIAL_MANAGER_URL="http://telesto.zapto.org:8081/SocialNetworkManager/Search/General/NonStandard/DriverMethod";
 
    @Autowired
    private JsonManager jsonManager;
    
    /**
     * Sends a REST request to the social network manager and converts the XML results into JSON
     * @param longitude
     * @param lat
     * @param area
     * @param searchTerm
     * @return
     * @throws ServletException
     */
    @RequestMapping(value="/tweetsfromlocation/{longitude}/{lat}/{area}/{searchTerm}.htm", method = RequestMethod.GET)
    public ResponseEntity<String> getNodesList(@PathVariable("longitude") Double longitude, @PathVariable("lat") Double lat, @PathVariable("area") Double area, @PathVariable("searchTerm") String searchTerm) throws ServletException {
        String tweetsList;
        String contentType="text/xml";
        String body="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
        		"<driverSpecificCall>"+
        		"<ClassName>TwitterDriver</ClassName><MethodName>SearchForTermUsingGeolocation</MethodName><ArgTypes>java.lang.String</ArgTypes><ArgTypes>java.lang.Integer</ArgTypes><ArgTypes>java.lang.Double</ArgTypes><ArgTypes>java.lang.Double</ArgTypes><ArgTypes>java.lang.Double</ArgTypes>"+
        		"<ArgValues>"+searchTerm+"</ArgValues>"+
        		"<ArgValues>100</ArgValues>"+
        		"<ArgValues>"+lat+"</ArgValues>"+
        		"<ArgValues>"+longitude+"</ArgValues>"+
        		"<ArgValues>"+area+"</ArgValues>"+
        		"</driverSpecificCall>";
		try {
			tweetsList = jsonManager.getTwitterXMLAsJson(TwitterController.SOCIAL_MANAGER_URL, body, contentType).toString();
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"success\" : false, \"message\" : \"authentication-failure\"}", JsonUtils.getJsonHeaders(), HttpStatus.NOT_FOUND);
		} 
        return new ResponseEntity<String>(tweetsList, JsonUtils.getJsonHeaders(), HttpStatus.OK);
    }
}