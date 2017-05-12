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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import otn.mobile.bl.TrackServicesHandler;
import otn.mobile.model.OtnServiceResponse;
import otn.mobile.model.OtnServiceDeleteTrackRequest;
import otn.mobile.model.OtnServiceGetTracksRequest;
import otn.mobile.model.OtnServiceGetTracksResponse;
import otn.mobile.model.OtnServiceLoadPublicTracksResponse;
import otn.mobile.model.OtnServiceLoadTrackRequest;
import otn.mobile.model.OtnServiceLoadTrackResponse;
import otn.mobile.model.OtnServicePoiRatings;
import otn.mobile.model.OtnServiceStatisticsResponse;
import otn.mobile.model.OtnServiceTrackIdRequest;
import otn.mobile.model.OtnServiceTrackRatingsRequest;
import otn.mobile.model.OtnServiceTrackRequest;
import otn.mobile.model.OtnServiceTrackResponse;
import otn.mobile.model.OtnServiceTrackVerificationRequest;
import otn.mobile.model.OtnServiceTrackVerificationResponse;
import otn.mobile.model.OtnServicesMyTracksRequest;
import otn.mobile.model.OtnServicesMyTracksResponse;
import static otn.mobile.services.Encryptor.decrypt;

/**
 *
 * @author EMantziou
 */
@Stateless
@Path("tracks")
public class trackServices {

    @PersistenceContext(unitName = "OTNplatformPU")
    private EntityManager em;

    private static final ResourceBundle p;

    private static final String key;

    private static final String initVector;

    private static Logger log = Logger.getLogger(trackServices.class.getName());

   
    static {

        p = ResourceBundle.getBundle("settings");

        key = p.getString("key");
        initVector = p.getString("initVector");
    }

    public void init() {
        EntityManagerFactory factory;
        factory = Persistence.createEntityManagerFactory("OTNplatformPU");
        em = factory.createEntityManager();

    }

    @POST
    @Path("/addTracks")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceTrackResponse addTracks(OtnServiceTrackRequest request) {

        if (em == null) {
            init();
        }
        Gson gson = new Gson();

        System.err.println("request " + gson.toJson(request, OtnServiceTrackRequest.class));

        log.info("Request for service: " + gson.toJson(request, OtnServiceTrackRequest.class));
        String csv = new String( request.getTrackFileCsv());

        System.err.println("track file csv " + csv);
        log.info("track file csv " + csv);
        //*********************** Variables ***************************
        TrackServicesHandler handler = new TrackServicesHandler(em);

        OtnServiceTrackResponse response = new OtnServiceTrackResponse();
        //************************* Action ****************************
        if (request == null || request.getDistance() == 0
                || request.getLat_start() == 0 || request.getLon_start() == 0
                || request.getTransportId() == 0 || request.getUserId() == null || request.getStart_address() == null
                || request.getAppId() == 0 || request.getDuration() == 0) {

            response.setResponseCode(10);
            response.setMessage("null params");
            return response;
        }

        if (request.toString().isEmpty() || request.getUserId().isEmpty()
                || request.getStart_address().isEmpty()) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response;
        }

        return handler.addTracks(request);
    }//end addTracks

    @POST
    @Path("/updateTrack")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceTrackResponse updateTrack(OtnServiceTrackRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        TrackServicesHandler handler = new TrackServicesHandler(em);

        OtnServiceTrackResponse response = new OtnServiceTrackResponse();
        //************************* Action ****************************
        System.out.println("start");
        if (request == null || request.getTrackId() == 0) {

            response.setResponseCode(10);
            response.setMessage("null params or empty params");
            return response;
        }

        if (request.toString().isEmpty()) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response;
        }

        return handler.updateTrack(request);
    }//end updateTracks

    @POST
    @Path("/getTrack")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceLoadTrackResponse getTrack(OtnServiceLoadTrackRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        TrackServicesHandler handler = new TrackServicesHandler(em);

        OtnServiceLoadTrackResponse response = new OtnServiceLoadTrackResponse();
        //************************* Action ****************************
        if (request == null || request.getTrackId() == 0 || request.getAppId() == 0) {

            response.setResponseCode(10);
            response.setMessage("null params");
            return response;
        }

        if (request.toString().isEmpty()) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response;
        }

        return handler.getTrack(request);
    }//end loadTrack

    @POST
    @Path("/getTrackEncrypted")
    @Consumes({"application/json", "application/xml","text/plain"})
    @Produces({"application/json", "application/xml","text/plain"})
    public String getTrackEncrypted(String requestEncrypted) {

        ResourceBundle p = ResourceBundle.getBundle("settings");

        String key = p.getString("key");
        String initVector = p.getString("initVector");

//        String key = "Otn12345Otn12345"; // 128 bit key
//        String initVector = "RandomInitVector"; // 16 bytes IV
        Gson gson = new Gson();

        String decrypted = decrypt(key, initVector, requestEncrypted);
        OtnServiceLoadTrackRequest request = gson.fromJson(decrypted, OtnServiceLoadTrackRequest.class);

        System.out.println("decrypted request " + decrypted);
        System.out.println(" request " + request.getAppId());
        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        TrackServicesHandler handler = new TrackServicesHandler(em);

        OtnServiceLoadTrackResponse response = new OtnServiceLoadTrackResponse();
        //************************* Action ****************************
        if (request == null || request.getTrackId() == 0 || request.getAppId() == 0) {

            response.setResponseCode(10);
            response.setMessage("null params");
            return response.toString();
        }

        if (request.toString().isEmpty()) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response.toString();
        }

        response = handler.getTrack(request);

        System.out.println("response in json " + gson.toJson(response));
        String encrypted = Encryptor.encrypt(key, initVector, gson.toJson(response).toString());
        return encrypted;
    }//end loadTrack

    @POST
    @Path("/getTracks")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceGetTracksResponse getTracks(OtnServiceGetTracksRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        TrackServicesHandler handler = new TrackServicesHandler(em);

        OtnServiceGetTracksResponse response = new OtnServiceGetTracksResponse();
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

        /*if (request.isIsPublic() && request.isIsMine()) {

         response.setResponseCode(0);
         response.setMessage("isPublic and isMine cannot be true simultaneously");
         return response;
         }*/
        return handler.getTracks(request);
    }//end getTracks

    @POST
    @Path("/myTracks")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServicesMyTracksResponse myTracks(OtnServicesMyTracksRequest request) {
        System.out.println("my tracks loading");

        if (em == null) {
            System.out.println("em is null");
            init();
        }

        //*********************** Variables ***************************
        TrackServicesHandler handler = new TrackServicesHandler(em);

        OtnServicesMyTracksResponse response = new OtnServicesMyTracksResponse();
        //************************* Action ****************************
        if (request == null || request.getUserId() == null) {

            response.setResponseCode(10);
            response.setMessage("null params");
            return response;
        }

        if (request.toString().isEmpty() || request.getUserId().isEmpty() || request.getAppId() == 0) {

            response.setResponseCode(10);
            response.setMessage("empty params");
            return response;
        }

        return handler.myTracks(request);
    }//end myTracks

    @POST
    @Path("/loadPublicTracks")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceLoadPublicTracksResponse loadPublicTracks() {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        TrackServicesHandler handler = new TrackServicesHandler(em);

        OtnServiceLoadPublicTracksResponse response = new OtnServiceLoadPublicTracksResponse();
        //************************* Action ****************************

        return handler.loadPublicTracks();
    }//end loadPublicTrack

    @POST
    @Path("/deleteTrack")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceResponse deleteTrack(OtnServiceDeleteTrackRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        TrackServicesHandler handler = new TrackServicesHandler(em);

        OtnServiceResponse response = new OtnServiceResponse();
        //************************* Action ****************************
        if (request == null || request.getTrackId() == 0 || request.getAppId() == 0
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

        return handler.deleteTrack(request);
    }//end deleteTrack

    @POST
    @Path("/addStatistics/{type}")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceStatisticsResponse addStatistics(@PathParam("type") String type, OtnServiceTrackIdRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        TrackServicesHandler handler = new TrackServicesHandler(em);

        OtnServiceStatisticsResponse response = new OtnServiceStatisticsResponse();
        //************************* Action ****************************
        if (request.getTrackId() == 0) {
            response.setResponseCode(10);
            response.setMessage("null params");
            return response;
        }

        if (!type.equals("views") && !type.equals("navigation")) {
            response.setResponseCode(10);
            response.setMessage("this type is unrecognizable");
            return response;
        }

        return handler.addStatistics(request.getTrackId(), type);

    }//end addStatistics

    @POST
    @Path("/getStatistics")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceStatisticsResponse getStatistics(OtnServiceTrackIdRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        TrackServicesHandler handler = new TrackServicesHandler(em);

        OtnServiceStatisticsResponse response = new OtnServiceStatisticsResponse();
        //************************* Action ****************************
        if (request.getTrackId() == 0) {
            response.setResponseCode(10);
            response.setMessage("null params");
            return response;
        }

        return handler.getStatistics(request.getTrackId());

    }//end getStatistics

    @POST
    @Path("/addTrackRatings")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceResponse addTrackRatings(OtnServiceTrackRatingsRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        TrackServicesHandler handler = new TrackServicesHandler(em);

        OtnServiceResponse response = new OtnServiceResponse();
        //************************* Action ****************************
        if (request.getTrackId() == 0 || request.getAppId() == 0
                || request.getUserId() == null || request.getUserId().isEmpty()) {
            response.setResponseCode(10);
            response.setMessage("null or empty params");
            return response;
        }

        if (request.getTrackRatings().size() < 0) {

            response.setResponseCode(10);
            response.setMessage("empty rating list");
            return response;

        }

        for (OtnServicePoiRatings trackRatingList : request.getTrackRatings()) {
            if (trackRatingList.getRate() == 0 || trackRatingList.getRatingTypeId() == 0) {
                response.setResponseCode(10);
                response.setMessage("empty or null params inside rating list");
                return response;
            }
        }

        return handler.addTrackRatings(request);

    }//end addTrackRatings

    @POST
    @Path("/addVerification")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public OtnServiceTrackVerificationResponse addVerification(OtnServiceTrackVerificationRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        TrackServicesHandler handler = new TrackServicesHandler(em);

        OtnServiceTrackVerificationResponse response = new OtnServiceTrackVerificationResponse();
        //************************* Action ****************************
        if (request.getTrackId() == 0 || request.getUserId() == null || request.getUserId().isEmpty()) {
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
    public OtnServiceTrackVerificationResponse getVerification(OtnServiceTrackVerificationRequest request) {

        if (em == null) {
            init();
        }

        //*********************** Variables ***************************
        TrackServicesHandler handler = new TrackServicesHandler(em);

        OtnServiceTrackVerificationResponse response = new OtnServiceTrackVerificationResponse();
        //************************* Action ****************************
        if (request.getTrackId() == 0) {
            response.setResponseCode(10);
            response.setMessage("null params");
            return response;
        }

        return handler.getVerification(request.getTrackId());

    }//end getVerification

}//end class
