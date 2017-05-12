/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.model;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author EMantziou
 */
public class OtnServiceWeatherRatings {

    private int weatherTypeId;

    public OtnServiceWeatherRatings() {
    }

    public OtnServiceWeatherRatings(int weatherTypeId) {
        this.weatherTypeId = weatherTypeId;
    }

    public int getWeatherTypeId() {
        return weatherTypeId;
    }

    public void setWeatherTypeId(int weatherTypeId) {
        this.weatherTypeId = weatherTypeId;
    }

   
}
