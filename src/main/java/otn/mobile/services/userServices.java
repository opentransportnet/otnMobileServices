/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import otn.mobile.bl.UserServicesHandler;
import otn.mobile.model.OtnServiceAddUserRequest;
import otn.mobile.model.OtnServiceDeleteUserContentRequest;
import otn.mobile.model.OtnServiceResponse;
import otn.mobile.model.OtnServiceTrackRequest;

/**
 *
 * @author EMantziou
 */
@Stateless
@Path("users")
public class userServices {

    @PersistenceContext(unitName = "OTNplatformPU")
    private EntityManager em;

    public void init() {
        EntityManagerFactory factory;
        factory = Persistence.createEntityManagerFactory("OTNplatformPU");
        em = factory.createEntityManager();
    }

    @POST
    @Path("/addUser")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceResponse addUsers(OtnServiceAddUserRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        UserServicesHandler handler = new UserServicesHandler(em);

        OtnServiceResponse response = new OtnServiceResponse();
        //************************* Action ****************************
        if (request == null || request.getUserId() == null) {

            response.setResponseCode(10);
            response.setMessage("null params");
            return response;
        }

        if (request.toString().isEmpty() || request.getUserId().isEmpty()) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response;
        }

        return handler.addUser(request);
    }//end addTracks

    @POST
    @Path("/deleteUserContent")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceResponse deleteUserContent(OtnServiceDeleteUserContentRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        UserServicesHandler handler = new UserServicesHandler(em);

        OtnServiceResponse response = new OtnServiceResponse();
        //************************* Action ****************************
        if (request == null || request.getUserId() == null || request.getAppId() == 0) {

            response.setResponseCode(10);
            response.setMessage("null params");
            return response;
        }

        if (request.toString().isEmpty() || request.getUserId().isEmpty()) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response;
        }

        return handler.deleteUserContent(request);
    }//end addTracks
}//end class
