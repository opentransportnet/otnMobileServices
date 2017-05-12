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
import otn.mobile.bl.IssueServicesHandler;
import otn.mobile.model.OtnServiceDeleteIssueTypeRequest;
import otn.mobile.model.OtnServiceGetIssueRequest;
import otn.mobile.model.OtnServiceGetIssueResponse;
import otn.mobile.model.OtnServiceIssueReportRequest;
import otn.mobile.model.OtnServiceIssueReportResponse;
import otn.mobile.model.OtnServiceLoadIssueRequest;
import otn.mobile.model.OtnServiceLoadIssueResponse;
import otn.mobile.model.OtnServiceLoadIssueTypeListResponse;
import otn.mobile.model.OtnServiceLoadIssueTypeResponse;
import otn.mobile.model.OtnServiceResponse;
import otn.mobile.model.OtnServiceUpdateIssueTypeRequest;

/**
 *
 * @author EMantziou
 */
@Stateless
@Path("issues")
public class issueServices {

    @PersistenceContext(unitName = "OTNplatformPU")
    private EntityManager em;

    public void init() {
        EntityManagerFactory factory;
        factory = Persistence.createEntityManagerFactory("OTNplatformPU");
        em = factory.createEntityManager();
    }

    @POST
    @Path("/issueReport")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceIssueReportResponse issueReport(OtnServiceIssueReportRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        IssueServicesHandler handler = new IssueServicesHandler(em);

        OtnServiceIssueReportResponse response = new OtnServiceIssueReportResponse();
        //************************* Action ****************************
        if (request == null || request.getDescription() == null
                || request.getDatetime().toString() == null || request.getIssueTypeId() == 0) {

            response.setResponseCode(10);
            response.setMessage("null params");
            return response;
        }

        if (request.toString().isEmpty() || request.getDescription().isEmpty()
                || request.getDatetime().toString().isEmpty()) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response;
        }

        return handler.issueReport(request);
    }

    @POST
    @Path("/loadIssues")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceLoadIssueResponse loadIssues(OtnServiceLoadIssueRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        IssueServicesHandler handler = new IssueServicesHandler(em);

        OtnServiceLoadIssueResponse response = new OtnServiceLoadIssueResponse();
        //************************* Action ****************************
        if (request == null) {

            response.setResponseCode(10);
            response.setMessage("null params");
            return response;
        }

        if (request.toString().isEmpty()) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response;
        }

        return handler.loadIssues(request);
    }

    @POST
    @Path("/loadIssue")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceGetIssueResponse loadIssue(OtnServiceGetIssueRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        IssueServicesHandler handler = new IssueServicesHandler(em);

        OtnServiceGetIssueResponse response = new OtnServiceGetIssueResponse();
        //************************* Action ****************************
        if (request == null || request.getIssueId()==0) {

            response.setResponseCode(10);
            response.setMessage("null params");
            return response;
        }

        if (request.toString().isEmpty()) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response;
        }

        return handler.loadIssue(request);
    }//end loadIssue

    @POST
    @Path("/addIssueType")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceResponse addIssueType(OtnServiceUpdateIssueTypeRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        IssueServicesHandler handler = new IssueServicesHandler(em);

        OtnServiceResponse response = new OtnServiceResponse();
        //************************* Action ****************************
        System.out.println("start");
        if (request == null || request.getIssueTypeId() == 0 || request.getName() == null || request.getAppId() == 0) {

            response.setResponseCode(10);
            response.setMessage("null params or empty params");
            return response;
        }

        if (request.toString().isEmpty() || request.getName().isEmpty()) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response;
        }

        return handler.addIssueType(request);
    }//end addIssueType

    @POST
    @Path("/updateIssueType")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceResponse updateIssueType(OtnServiceUpdateIssueTypeRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        IssueServicesHandler handler = new IssueServicesHandler(em);

        OtnServiceResponse response = new OtnServiceResponse();
        //************************* Action ****************************
        System.out.println("start");
        if (request == null || request.getIssueTypeId() == 0 || request.getName() == null || request.getAppId() == 0) {

            response.setResponseCode(10);
            response.setMessage("null params or empty params");
            return response;
        }

        if (request.toString().isEmpty() || request.getName().isEmpty()) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response;
        }

        return handler.updateIssueType(request);
    }//end updateIssueType

    @POST
    @Path("/getIssueType")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceLoadIssueTypeResponse getIssueType(OtnServiceUpdateIssueTypeRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        IssueServicesHandler handler = new IssueServicesHandler(em);

        OtnServiceLoadIssueTypeResponse response = new OtnServiceLoadIssueTypeResponse();
        //************************* Action ****************************
        System.out.println("start");
        if (request == null || request.getIssueTypeId() == 0) {

            response.setResponseCode(10);
            response.setMessage("null params or empty params");
            return response;
        }

        if (request.toString().isEmpty()) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response;
        }

        return handler.getIssueType(request);
    }//end getIssueType

    @POST
    @Path("/getIssueTypes")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceLoadIssueTypeListResponse getIssueTypes(OtnServiceUpdateIssueTypeRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        IssueServicesHandler handler = new IssueServicesHandler(em);

        OtnServiceLoadIssueTypeListResponse response = new OtnServiceLoadIssueTypeListResponse();
        //************************* Action ****************************
        System.out.println("start");
        if (request == null || request.getAppId() == 0) {

            response.setResponseCode(10);
            response.setMessage("null params or empty params");
            return response;
        }

        if (request.toString().isEmpty()) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response;
        }

        return handler.getIssueTypes(request);
    }//end getIssueTypes

    @POST
    @Path("/deleteIssueType")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceResponse deleteIssueType(OtnServiceDeleteIssueTypeRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        IssueServicesHandler handler = new IssueServicesHandler(em);

        OtnServiceResponse response = new OtnServiceResponse();
        //************************* Action ****************************
        if (request == null || request.getIssueTypeId() == 0) {

            response.setResponseCode(10);
            response.setMessage("null params");
            return response;
        }

        if (request.toString().isEmpty()) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response;
        }

        return handler.deleteIssueType(request);
    }//end deleteTrack

}// end class
