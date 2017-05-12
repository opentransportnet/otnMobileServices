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
public class OtnServiceVerificationResponse {

    private int responseCode;

    private String message;

    private int verification;

    private int poiId;
    
    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getVerification() {
        return verification;
    }

    public void setVerification(int verification) {
        this.verification = verification;
    }

    public int getPoiId() {
        return poiId;
    }

    public void setPoiId(int poiId) {
        this.poiId = poiId;
    }

    
    
}//end class
