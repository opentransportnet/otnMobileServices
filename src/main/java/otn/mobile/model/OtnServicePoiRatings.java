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
public class OtnServicePoiRatings {
    private int ratingTypeId;
    
    private int rate;
    
    public OtnServicePoiRatings(){
        }
    
    public OtnServicePoiRatings(int ratingTypeId, int rate){
        this.ratingTypeId = ratingTypeId;
        this.rate = rate;
    }
    
    public int getRatingTypeId() {
        return ratingTypeId;
    }

    public void setRatingTypeId(int ratingTypeId) {
        this.ratingTypeId = ratingTypeId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

   
}
