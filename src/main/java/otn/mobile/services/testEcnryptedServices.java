/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.services;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.util.HashMap;
import java.util.ResourceBundle;
import otn.mobile.model.OtnServiceLoadPoiRequest;
import otn.mobile.model.OtnServiceLoadTrackRequest;
import otn.mobile.model.OtnServiceTrackIdRequest;
import static otn.mobile.services.Encryptor.decrypt;
import static otn.mobile.services.Encryptor.encrypt;

/**
 *
 * @author EMantziou
 */
public class testEcnryptedServices {

    private final WebResource webResource;
    private final Client client;
    private final ClientConfig config;

    // for encrypt/decrypt
    private static String key; // 128 bit key
    private static String initVector; // 16 bytes IV

    private static String BASE_URI;

    private static final ResourceBundle p;

    static {

        p = ResourceBundle.getBundle("settings");

    }

    public testEcnryptedServices() {

        //************************ Variables *************************
        //file with necessary parameters
        config = new DefaultClientConfig();

        try {

            key = p.getString("key");
            initVector = p.getString("initVector");
            BASE_URI = p.getString("BASE_URI");

        } catch (Exception e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //************************ Action *************************
        //   System.out.println("baseUrl:"+baseUrl);
        client = Client.create(config);

        webResource = client.resource(BASE_URI);

    }

    public <T> T getTrack(Class<T> responseType) {

        System.out.println("into client");
        WebResource resource = webResource;

        OtnServiceLoadTrackRequest request = new OtnServiceLoadTrackRequest();

        request.setUserId("Leo12345test");
        request.setAppId(10);
        request.setTrackId(29);

        Gson gson = new Gson();

        String requestEncrypted = encrypt(key, initVector, gson.toJson(request).toString());

        return resource.path("tracks").path("getTrackEncrypted").type(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(responseType, requestEncrypted);
    }

    public <T> T getTrackStatistics(Class<T> responseType) {

        System.out.println("into get statistics client");
        WebResource resource = webResource;

        OtnServiceTrackIdRequest request = new OtnServiceTrackIdRequest();

        request.setTrackId(1);

        Gson gson = new Gson();

        String requestEncrypted = encrypt(key, initVector, gson.toJson(request).toString());

        return resource.path("tracks").path("getStatistics").type(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(responseType, requestEncrypted);
    }

    public <T> T getPOI(Class<T> responseType) {

        System.out.println("into client");
        WebResource resource = webResource;

        OtnServiceLoadPoiRequest request = new OtnServiceLoadPoiRequest();

        request.setAppId(10);
        request.setUserId("Leo12345test");
        request.setPoiId(1);

        Gson gson = new Gson();

        String requestEncrypted = encrypt(key, initVector, gson.toJson(request).toString());

        return resource.path("pois").path("loadPoiEncrypted").type(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(responseType, requestEncrypted);
    }

    public void close() {
        client.destroy();
    }

    public static void main(String[] args) {
        //********************Variables********************************

        // call service and get response
        ClientResponse response;
        String respString;
        testEcnryptedServices client = new testEcnryptedServices();

        //********************Action********************************
        System.out.println("inside main..");

        // getTrack
//        response = client.getTrack(ClientResponse.class);
        //getPOI
//        response = client.getPOI(ClientResponse.class);
        
        //get track statistics
        response = client.getTrackStatistics(ClientResponse.class);

        //close client
        client.close();

        // get response from service
        respString = response.getEntity(String.class);

        /**
         * start encryption
         */
//        String encrypted = encrypt(key, initVector, respString);
        
         System.out.println("encrypted response " + respString);
        /**
         * start encryption
         */
        String decrypted = decrypt(key, initVector, respString);
//        System.out.println(decrypt(key, initVector,
//                encrypt(key, initVector, "Hello World")));

        System.out.println("decrypted response " + decrypted);
    }

}// end class
