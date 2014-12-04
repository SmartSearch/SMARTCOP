/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.storage.classes;

import java.util.Properties;

/**
 *
 * @author albert
 */
public class EdgeNode {

    public static String NODE_NAME = "node.name";
    public static String NODE_ADDRESS = "node.address";
    public static String COUCHDB_ADDRESS = "couchdb.address";
    private Properties nodes = new Properties();

    public EdgeNode() {
    }

    public void addAttribute(String name, String value) {
        this.nodes.setProperty(name, value);
    }

    public String getAttribute(String name) {
        return this.nodes.getProperty(name);
    }
}
