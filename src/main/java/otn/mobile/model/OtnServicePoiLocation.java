/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.model;

/**
 *
 * @author EMantziou
 */
public class OtnServicePoiLocation {
    
    private String type;
    
    private OtnServiceProperties properties;
    
    private OtnServiceGeometry geometry;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OtnServiceProperties getProperties() {
        return properties;
    }

    public void setProperties(OtnServiceProperties properties) {
        this.properties = properties;
    }

    public OtnServiceGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(OtnServiceGeometry geometry) {
        this.geometry = geometry;
    }

    

}
