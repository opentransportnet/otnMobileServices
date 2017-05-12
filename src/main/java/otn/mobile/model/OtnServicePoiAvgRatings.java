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
public class OtnServicePoiAvgRatings {

    private int ratingTypeId;

    private double avgRate;

    public OtnServicePoiAvgRatings() {
    }

    public OtnServicePoiAvgRatings(int ratingTypeId, double avgRate) {
        this.ratingTypeId = ratingTypeId;
        this.avgRate = avgRate;
    }

    public int getRatingTypeId() {
        return ratingTypeId;
    }

    public void setRatingTypeId(int ratingTypeId) {
        this.ratingTypeId = ratingTypeId;
    }

    public double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(double avgRate) {
        this.avgRate = avgRate;
    }

}
