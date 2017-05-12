/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.services;

import com.google.gson.Gson;
import java.util.ResourceBundle;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import otn.mobile.bl.PoisServicesHandler;
import otn.mobile.bl.TrackServicesHandler;
import otn.mobile.model.OtnServiceAddPoiRequest;
import otn.mobile.model.OtnServiceAddPoiResponse;
import otn.mobile.model.OtnServiceAddPublicPoiRequest;
import otn.mobile.model.OtnServiceDeletePoiRequest;
import otn.mobile.model.OtnServicePublicPoiRequest;
import otn.mobile.model.OtnServiceLoadPoiRequest;
import otn.mobile.model.OtnServiceLoadPoiResponse;
import otn.mobile.model.OtnServiceLoadPublicPoiResponse;
import otn.mobile.model.OtnServiceLoadTrackRequest;
import otn.mobile.model.OtnServiceLoadTrackResponse;
import otn.mobile.model.OtnServiceResponse;
import otn.mobile.model.OtnServicePoiIdRequest;
import otn.mobile.model.OtnServicePoiRatings;
import otn.mobile.model.OtnServicePoiRatingsRequest;
import otn.mobile.model.OtnServiceVerificationResponse;
import static otn.mobile.services.Encryptor.decrypt;

/**
 *
 * @author EMantziou
 */
@Stateless
@Path("pois")
public class poiServices {

    @PersistenceContext(unitName = "OTNplatformPU")
    private EntityManager em;

    public void init() {
        EntityManagerFactory factory;
        factory = Persistence.createEntityManagerFactory("OTNplatformPU");
        em = factory.createEntityManager();
    }

    @POST
    @Path("/addPois")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceAddPoiResponse addPois(OtnServiceAddPoiRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        PoisServicesHandler handler = new PoisServicesHandler(em);

        OtnServiceAddPoiResponse response = new OtnServiceAddPoiResponse();
        //************************* Action ****************************
        if (request == null || request.getAppId() == 0 || request.getUserId() == null
                || request.getTransportTypeId() == 0 || request.getName() == null) {

            response.setResponseCode(10);
            response.setMessage("null params");
            return response;
        }

        if (request.toString().isEmpty() || request.getName().isEmpty() || request.getUserId().isEmpty()) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response;
        }

        return handler.addPois(request);
    }//end addPois

    @POST
    @Path("/loadPoi")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceLoadPoiResponse loadPoi(OtnServiceLoadPoiRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        PoisServicesHandler handler = new PoisServicesHandler(em);

        OtnServiceLoadPoiResponse response = new OtnServiceLoadPoiResponse();
        //************************* Action ****************************
        if (request == null || request.getPoiId() == 0 || request.getAppId() == 0) {

            response.setResponseCode(10);
            response.setMessage("null params");
            return response;
        }

        if (request.toString().isEmpty()) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response;
        }

        return handler.loadPoi(request);
    }//end loadPoi

    @POST
    @Path("/loadPoiEncrypted")
    @Consumes({"application/json", "application/xml", "text/plain"})
    @Produces({"application/json", "application/xml", "text/plain"})
    public String loadPoiEncrypted(String requestEncrypted) {

        ResourceBundle p = ResourceBundle.getBundle("settings");

        String key = p.getString("key");
        String initVector = p.getString("initVector");

        Gson gson = new Gson();

        String decrypted = decrypt(key, initVector, requestEncrypted);
        OtnServiceLoadPoiRequest request = gson.fromJson(decrypted, OtnServiceLoadPoiRequest.class);

//        System.out.println("decrypted request " + decrypted);
        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        PoisServicesHandler handler = new PoisServicesHandler(em);

        OtnServiceLoadPoiResponse response = new OtnServiceLoadPoiResponse();
        //************************* Action ****************************
        if (request == null || request.getUserId() == null
                || request.getPoiId() == 0 || request.getAppId() == 0) {

            response.setResponseCode(10);
            response.setMessage("null params");
            return response.toString();
        }

        if (request.toString().isEmpty() || request.getUserId().isEmpty()) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response.toString();
        }

        response = handler.loadPoi(request);

        System.out.println("response in json " + gson.toJson(response));
        String encrypted = Encryptor.encrypt(key, initVector, gson.toJson(response).toString());
        return encrypted;
    }//end loadPoiEcnrypted

    @POST
    @Path("/deletePoi")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceResponse deletePoi(OtnServiceDeletePoiRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        PoisServicesHandler handler = new PoisServicesHandler(em);

        OtnServiceResponse response = new OtnServiceResponse();
        //************************* Action ****************************
        if (request == null || request.getPoiId() == 0 || request.getAppId() == 0
                || request.getUserId() == null) {

            response.setResponseCode(10);
            response.setMessage("null params");
            return response;
        }

        if (request.toString().isEmpty() || request.getUserId().isEmpty()) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response;
        }

        return handler.deletePoi(request);
    }// end deletePoi

    @POST
    @Path("/addPublicPois")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceAddPoiResponse addPublicPois(OtnServiceAddPublicPoiRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        PoisServicesHandler handler = new PoisServicesHandler(em);

        OtnServiceAddPoiResponse response = new OtnServiceAddPoiResponse();
        //************************* Action ****************************
        if (request == null || request.toString().isEmpty() || request.getLocation().getGeometry().getLatitude() == 0
                || request.getLocation().getGeometry().getLongitude() == 0 || request.getCategoryId() == 0) {

            response.setResponseCode(10);
            response.setMessage("empty or null params");
            return response;
        }

        return handler.addPublicPois(request);
    }//end addPublicPois

    @POST
    @Path("/loadPublicPoi")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceLoadPublicPoiResponse loadPublicPoi(OtnServicePublicPoiRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        PoisServicesHandler handler = new PoisServicesHandler(em);

        OtnServiceLoadPublicPoiResponse response = new OtnServiceLoadPublicPoiResponse();
        //************************* Action ****************************
        if (request == null || request.getPoiId() == 0) {

            response.setResponseCode(10);
            response.setMessage("null params");
            return response;
        }

        if (request.toString().isEmpty()) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response;
        }

        return handler.loadPublicPoi(request);
    }//end loadPublicPoi

    @POST
    @Path("/deletePublicPoi")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceResponse deletePublicPoi(OtnServicePublicPoiRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        PoisServicesHandler handler = new PoisServicesHandler(em);

        OtnServiceResponse response = new OtnServiceResponse();
        //************************* Action ****************************
        if (request == null || request.getPoiId() == 0) {

            response.setResponseCode(10);
            response.setMessage("null params");
            return response;
        }

        if (request.toString().isEmpty()) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response;
        }

        return handler.deletePublicPoi(request);
    }// end deletePublicPoi

    @POST
    @Path("/addVerification")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceVerificationResponse addVerification(OtnServicePoiIdRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        PoisServicesHandler handler = new PoisServicesHandler(em);

        OtnServiceVerificationResponse response = new OtnServiceVerificationResponse();
        //************************* Action ****************************
        if (request.getPoiId() == 0 || request.getUserId() == null || request.getUserId().isEmpty()) {
            response.setResponseCode(10);
            response.setMessage("null or empty params");
            return response;
        }

        return handler.addVerification(request);

    }//end addVerification

    @POST
    @Path("/getVerification")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceVerificationResponse getVerification(OtnServicePoiIdRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        PoisServicesHandler handler = new PoisServicesHandler(em);

        OtnServiceVerificationResponse response = new OtnServiceVerificationResponse();
        //************************* Action ****************************
        if (request.getPoiId() == 0) {
            response.setResponseCode(10);
            response.setMessage("null params");
            return response;
        }

        return handler.getVerification(request.getPoiId(), request.isIs_public());

    }//end getVerification

    @POST
    @Path("/addPoiRatings")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceResponse addPoiRatings(OtnServicePoiRatingsRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        PoisServicesHandler handler = new PoisServicesHandler(em);

        OtnServiceResponse response = new OtnServiceResponse();
        //************************* Action ****************************

        if (request.isIs_public() == null || !request.isIs_public() ) {
            if (request.getPoiId() == 0 || request.getAppId() == 0
                    || request.getUserId() == null || request.getUserId().isEmpty()) {
                response.setResponseCode(10);
                response.setMessage("null or empty params");
                return response;
            }
        } else {
            if (request.getPoiId() == 0 || request.getUserId() == null || request.getUserId().isEmpty()) {
                response.setResponseCode(10);
                response.setMessage("null or empty params");
                return response;
            }

        }

        if (request.getPoiRatings().size() < 0) {

            response.setResponseCode(10);
            response.setMessage("empty rating list");
            return response;

        }

        for (OtnServicePoiRatings poiRatingList : request.getPoiRatings()) {
            if (poiRatingList.getRate() == 0 || poiRatingList.getRatingTypeId() == 0) {
                response.setResponseCode(10);
                response.setMessage("empty or null params inside rating list");
                return response;
            }
        }

        return handler.addPoiRatings(request);

    }//end addPoiRatings

}//end class
