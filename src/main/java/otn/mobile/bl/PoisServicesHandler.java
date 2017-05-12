/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.bl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import otn.mobile.model.OtnServiceAddPoiRequest;
import otn.mobile.model.OtnServiceAddPoiResponse;
import otn.mobile.model.OtnServiceAddPublicPoiRequest;
import otn.mobile.model.OtnServiceDeletePoiRequest;
import otn.mobile.model.OtnServicePublicPoiRequest;
import otn.mobile.model.OtnServiceGeometry;
import otn.mobile.model.OtnServiceLoadPoiRequest;
import otn.mobile.model.OtnServiceLoadPoiResponse;
import otn.mobile.model.OtnServiceLoadPublicPoiResponse;
import otn.mobile.model.OtnServicePoiAvgRatings;
import otn.mobile.model.OtnServicePoiIdRequest;
import otn.mobile.model.OtnServiceResponse;
import otn.mobile.model.OtnServicePoiLocation;
import otn.mobile.model.OtnServicePoiRatings;
import otn.mobile.model.OtnServicePoiRatingsRequest;
import otn.mobile.model.OtnServiceProperties;
import otn.mobile.model.OtnServiceVerificationResponse;
import otn.mobile.persistency.Apps;
import otn.mobile.persistency.Poi;
import otn.mobile.persistency.PoiRating;
import otn.mobile.persistency.PoiRatingType;
import otn.mobile.persistency.PoiVerified;
import otn.mobile.persistency.PublicPoi;
import otn.mobile.persistency.PublicPoiCategory;
import otn.mobile.persistency.PublicPoiRating;
import otn.mobile.persistency.PublicPoiVerified;
import otn.mobile.persistency.Source;
import otn.mobile.persistency.TransportType;
import otn.mobile.persistency.User;

/**
 *
 * @author EMantziou
 */
public class PoisServicesHandler {

    private EntityManager em;

    public PoisServicesHandler(EntityManager em) {
        this.em = em;
    }

    public OtnServiceAddPoiResponse addPois(OtnServiceAddPoiRequest request) {

        OtnServiceAddPoiResponse response = new OtnServiceAddPoiResponse();

        User users;

        TransportType transport_type;

        Apps app;

        Source source = null;

        Poi poi = new Poi();

        PoiRating poi_rating;

        PoiRatingType poi_rating_type;

        try {
            //**********************find user*****************************

            users = em.find(User.class, request.getUserId());

            if (users == null) {
                System.out.println("=====================================");
                System.out.println("user not found");
                em.getTransaction().begin();
                users = new User();
                users.setUserId(request.getUserId());
                em.persist(users);
                em.flush();
                em.getTransaction().commit();
                em.clear();
                System.out.println("user created");

//                response.setMessage("user does not exist");
//                response.setResponseCode(1);
//                return response;
            }

            //***********************find transportID******************************
            transport_type = em.find(TransportType.class, request.getTransportTypeId());

            if (transport_type == null) {

                response.setMessage("transport type id does not exist");
                response.setResponseCode(1);
                return response;
            }

            //***********************find app name******************************
            app = em.find(Apps.class, request.getAppId());

            if (app == null) {

                response.setMessage("application does not exist");
                response.setResponseCode(1);
                return response;
            }

            //***********************find sourceId******************************
            if (request.getPoiSourceId() != 0) {
                source = em.find(Source.class, request.getPoiSourceId());

                if (source == null) {
                    response.setMessage("source type id does not exist");
                    response.setResponseCode(1);
                    return response;
                }
            }
            em.getTransaction().begin();
            poi.setAddress(request.getAddress());
            poi.setAppId(app);
            poi.setLatitude(request.getLocation().getGeometry().getLatitude());
            poi.setLongitude(request.getLocation().getGeometry().getLongitude());
            poi.setDescription(request.getDescription());
            poi.setName(request.getName());
            poi.setPoiSourceId(source);
            poi.setRating(request.getRating());
            poi.setTransportTypeId(transport_type);
            poi.setUserId(users);
            poi.setVerified(request.getVerified());

            if (request.getPoiRatings().size() > 0) {

                OtnServiceAddPoiResponse rate_response = checkPoiRateAndType(request.getPoiRatings());

                if (rate_response.getResponseCode() == 0) {
                    em.persist(poi);
                    em.flush();
                    em.getTransaction().commit();
                    em.clear();

                    for (OtnServicePoiRatings poiRatingList : request.getPoiRatings()) {
                        em.getTransaction().begin();
                        poi_rating = new PoiRating();
                        poi_rating_type = em.find(PoiRatingType.class, poiRatingList.getRatingTypeId());
                        poi_rating.setPoiId(poi);
                        poi_rating.setPoiRatingTypeId(poi_rating_type);
                        poi_rating.setRate(poiRatingList.getRate());
                        poi_rating.setUserId(users);

                        em.persist(poi_rating);
                        em.flush();
                        em.getTransaction().commit();
                        em.clear();

                        response.setMessage("Poi added successfully");
                        response.setResponseCode(0);
                        response.setPoiId(poi.getPoiId());

                    }

                } else {

                    response.setMessage(rate_response.getMessage());
                    response.setResponseCode(rate_response.getResponseCode());
                    return response;

                }

            } else {
                em.persist(poi);
                em.flush();
                em.getTransaction().commit();
                em.clear();

                response.setMessage("Poi added successfully");
                response.setResponseCode(0);
                response.setPoiId(poi.getPoiId());
                return response;
            }

            return response;
        } catch (Exception e) {
            response.setMessage("something went totally wrong");
            response.setResponseCode(1);
            return response;

        } finally {
            em.clear();
        }

    }//end addPoi

    public OtnServiceLoadPoiResponse loadPoi(OtnServiceLoadPoiRequest request) {

        //*********************** Variables ***************************
        OtnServiceLoadPoiResponse response = new OtnServiceLoadPoiResponse();

        User users = null;

        TypedQuery query;

        List<Poi> poiparamsList;

//        List<PoiRating> poiratingsparamsList;
        Poi poi;

        ArrayList<OtnServicePoiAvgRatings> poiRatingList = null;

        OtnServicePoiLocation location = new OtnServicePoiLocation();

        OtnServiceProperties properties = new OtnServiceProperties();

        OtnServiceGeometry geometry = new OtnServiceGeometry();

        //************************* Action ****************************
        System.out.println(
                "------------Start---------------");

        try {

            //***************find pois****************************
            query = (TypedQuery) em.createQuery("SELECT p FROM Poi p WHERE p.poiId = :poiId");

            query.setParameter("poiId", request.getPoiId());

            //we use list to avoid "not found" exception
            poiparamsList = query.getResultList();

            //if we found no results, the users has no poi with this trackId
            //so return error message
            if (poiparamsList.isEmpty()) {
                response.setMessage("no available poi");
                response.setResponseCode(1);
                return response;

            } else {

                poi = poiparamsList.get(0);

//                query = (TypedQuery) em.createQuery("SELECT p FROM PoiRating p WHERE p.userId = :userId and p.poiId = :poiId");
                query = (TypedQuery) em.createQuery("SELECT p.poiRatingTypeId, AVG(p.rate) AS avgrates FROM PoiRating p WHERE p.poiId = :poiId GROUP BY p.poiRatingTypeId ");

                query.setParameter("poiId", poi);
//                query.setParameter("userId", users);

                //we use list to avoid "not found" exception
                List poiratingsparamsList = query.getResultList();

                Iterator itr = poiratingsparamsList.iterator();
                if (!poiratingsparamsList.isEmpty()) {
                    poiRatingList = new ArrayList<OtnServicePoiAvgRatings>();
                    while (itr.hasNext()) {
                        Object[] obj = (Object[]) itr.next();
                        PoiRatingType ratingtype = (PoiRatingType) obj[0];
                        Double avgRate = Double.parseDouble(String.valueOf(obj[1]));
//                        System.out.println("rate type id  " + ratingtype.getPoiRatingTypeId() + "rating avg  " + avgRate);
                        poiRatingList.add(new OtnServicePoiAvgRatings(ratingtype.getPoiRatingTypeId(), avgRate));
//
                    }
                }

//                if (!poiratingsparamsList.isEmpty()) {
//                    poiRatingList = new ArrayList<OtnServicePoiRatings>();
//
//                    for (PoiRating poiRating : poiratingsparamsList) {
//
//                        poiRatingList.add(new OtnServicePoiRatings(poiRating.getPoiRatingTypeId().getPoiRatingTypeId(), poiRating.getRate()));
//
//                    }
//                }
                location.setType("Feature");
                properties.setName("Location");
                location.setProperties(properties);
                geometry.setType("Point");
                geometry.setLatitude(poi.getLatitude());
                geometry.setLongitude(poi.getLongitude());
                location.setGeometry(geometry);

                response.setMessage("succesfull loading");
                response.setResponseCode(0);
                response.setName(poi.getName());
                response.setPoiId(poi.getPoiId());
                response.setDescription(poi.getDescription());
                response.setTransportName(poi.getTransportTypeId().getName());
                response.setAddress(poi.getAddress());
                response.setAppId(poi.getAppId().getAppId());
                response.setPoiRatings(poiRatingList);
                if (poi.getPoiSourceId() != null) {
                    response.setPoiSourceName(poi.getPoiSourceId().getName());
                }
                response.setVerified(poi.getVerified());
                response.setLocation(location);

                //if user exists find if verified the poi or not
                if (request.getUserId() == null || !request.getUserId().isEmpty()) {
                    //**********************find user*****************************
                    users = em.find(User.class, request.getUserId());

                    if (users == null) {
                        response.setMessage("user does not exist");
                        response.setResponseCode(1);
                        return response;
                    } else {

                        query = (TypedQuery) em.createQuery("SELECT p FROM PoiVerified p WHERE p.poiId = :poiId and p.userId =  :userId");

                        query.setParameter("poiId", poi);
                        query.setParameter("userId", users);

                        if (query.getResultList().isEmpty()) {
                            response.setUser_verified(false);
                        } else {
                            response.setUser_verified(true);
                        }

                    }
                }

            }

            response.setMessage("success");
            response.setResponseCode(0);

        } catch (Exception e) {
//            e.printStackTrace();

            response.setMessage("failure");
            response.setResponseCode(2);
        } finally {
            return response;
        }
    }//end loadPoi

    public OtnServiceResponse deletePoi(OtnServiceDeletePoiRequest request) {
        OtnServiceResponse response = new OtnServiceResponse();

        Poi poi;
        try {
            em.getTransaction().begin();
            poi = em.find(Poi.class, request.getPoiId());

            em.remove(poi);
            em.flush();
            em.getTransaction().commit();

            response.setMessage("success");
            response.setResponseCode(0);

            return response;
        } catch (Exception e) {

            response.setMessage("failure");
            response.setResponseCode(2);
            return response;
        } finally {
            em.clear();
        }

    }//end deletePoi()

    public OtnServiceAddPoiResponse addPublicPois(OtnServiceAddPublicPoiRequest request) {

        OtnServiceAddPoiResponse response = new OtnServiceAddPoiResponse();

        PublicPoi public_poi = new PublicPoi();

        PublicPoiRating public_poi_rating;

        PoiRatingType poi_rating_type;

        PublicPoiCategory public_poi_category;

        int verify = 0;
        try {
            //**********************find category*****************************
            public_poi_category = em.find(PublicPoiCategory.class, request.getCategoryId());

            if (public_poi_category == null) {

                response.setMessage("category id does not exist");
                response.setResponseCode(1);
                return response;
            }

            if (request.getVerified() != 0) {
                response.setMessage("owner cannot verify his Public POI");
                response.setResponseCode(1);
                return response;
            }

            em.getTransaction().begin();

            public_poi.setLatitude(request.getLocation().getGeometry().getLatitude());
            public_poi.setLongitude(request.getLocation().getGeometry().getLongitude());

            if (request.getRating() != 0) {
                public_poi.setRate(request.getRating());
            }
            public_poi.setCategoryId(public_poi_category);
            public_poi.setVerify(verify);

            //****************check if ratings exist and them to the public_poi_rating table*******************
//            if (request.getPoiRatings().size() > 0) {
//
//                OtnServiceAddPoiResponse rate_response = checkPoiRateAndType(request.getPoiRatings());
//
//                if (rate_response.getResponseCode() == 0) {
//                    em.persist(public_poi);
//                    em.flush();
//                    em.getTransaction().commit();
//                    em.clear();
//
//                    for (OtnServicePoiRatings poiRatingList : request.getPoiRatings()) {
//                        em.getTransaction().begin();
//                        public_poi_rating = new PublicPoiRating();
//                        poi_rating_type = em.find(PoiRatingType.class, poiRatingList.getRatingTypeId());
//                        public_poi_rating.setPublicPoiId(public_poi);
//                        public_poi_rating.setPoiRatingTypeId(poi_rating_type);
//                        public_poi_rating.setRate(poiRatingList.getRate());
//                     
//
//                        em.persist(public_poi_rating);
//                        em.flush();
//                        em.getTransaction().commit();
//                        em.clear();
//
//                        response.setMessage("Poi added successfully");
//                        response.setResponseCode(0);
//                        response.setPoiId(public_poi.getPublicPoiId());
//
//                    }
//
//                } else {
//                    response.setMessage(rate_response.getMessage());
//                    response.setResponseCode(rate_response.getResponseCode());
//                    return response;
//                }
//            } else {
            em.persist(public_poi);
            em.flush();
            em.getTransaction().commit();
            em.clear();

            response.setMessage("Public Poi added successfully");
            response.setResponseCode(0);
            response.setPoiId(public_poi.getPublicPoiId());
            return response;
//            }

//            return response;
        } catch (Exception e) {
            if (e.getLocalizedMessage().contains("already exists")) {
                response.setMessage("public poi with these coordinates and category id already added");
                response.setResponseCode(1);
                return response;
            } else {
                response.setMessage("something went totally wrong");
                response.setResponseCode(2);
                return response;
            }
        } finally {
            em.clear();
        }

    }//end addPublicPoi

    public OtnServiceLoadPublicPoiResponse loadPublicPoi(OtnServicePublicPoiRequest request) {

        //*********************** Variables ***************************
        OtnServiceLoadPublicPoiResponse response = new OtnServiceLoadPublicPoiResponse();

        TypedQuery query;

        PublicPoi public_poi;

        ArrayList<OtnServicePoiAvgRatings> poiRatingList = null;

        OtnServicePoiLocation location = new OtnServicePoiLocation();

        OtnServiceProperties properties = new OtnServiceProperties();

        OtnServiceGeometry geometry = new OtnServiceGeometry();

        //************************* Action ****************************
        System.out.println("------------Start---------------");
        try {

            //***************find user's tracks****************************
            public_poi = em.find(PublicPoi.class, request.getPoiId());

            //if we found no results, the users has no poi with this trackId
            //so return error message
            if (public_poi == null) {
                response.setMessage("public poi id does not exist");
                response.setResponseCode(1);
                return response;

            } else {
                query = (TypedQuery) em.createQuery("SELECT p.poiRatingTypeId, AVG(p.rate) AS avgrates FROM PublicPoiRating p WHERE p.publicPoiId = :poiId GROUP BY p.poiRatingTypeId ");

                query.setParameter("poiId", public_poi);
//                query.setParameter("userId", users);

                //we use list to avoid "not found" exception
                List poiratingsparamsList = query.getResultList();

                Iterator itr = poiratingsparamsList.iterator();
                if (!poiratingsparamsList.isEmpty()) {
                    poiRatingList = new ArrayList<OtnServicePoiAvgRatings>();
                    while (itr.hasNext()) {
                        Object[] obj = (Object[]) itr.next();
                        PoiRatingType ratingtype = (PoiRatingType) obj[0];
                        Double avgRate = Double.parseDouble(String.valueOf(obj[1]));
//                        System.out.println("rate type id  " + ratingtype.getPoiRatingTypeId() + "rating avg  " + avgRate);
                        poiRatingList.add(new OtnServicePoiAvgRatings(ratingtype.getPoiRatingTypeId(), avgRate));
//
                    }
                }

                location.setType("Feature");
                properties.setName("Location");
                location.setProperties(properties);
                geometry.setType("Point");
                geometry.setLatitude(public_poi.getLatitude());
                geometry.setLongitude(public_poi.getLongitude());
                location.setGeometry(geometry);

                response.setMessage("succesfull loading");
                response.setResponseCode(0);

                response.setPoiId(public_poi.getPublicPoiId());
                response.setCategoryName(public_poi.getCategoryId().getName());

                if (poiRatingList != null) {
                    response.setPoiRatings(poiRatingList);
                }

                if (public_poi.getVerify() != null) {
                    response.setVerified(public_poi.getVerify());
                }

                if (public_poi.getRate() != null) {
                    response.setRate(public_poi.getRate());
                }

                response.setLocation(location);
            }
        } catch (Exception e) {
            e.printStackTrace();

            response.setMessage("failure");
            response.setResponseCode(2);
        } finally {

            return response;
        }
    }//end loadPublicPoi

    public OtnServiceResponse deletePublicPoi(OtnServicePublicPoiRequest request) {
        OtnServiceResponse response = new OtnServiceResponse();

        PublicPoi public_poi;
        try {
            em.getTransaction().begin();
            public_poi = em.find(PublicPoi.class, request.getPoiId());
            if (public_poi == null) {
                response.setMessage("public poi does not exist");
                response.setResponseCode(1);
                return response;
            } else {
                em.remove(public_poi);
                em.flush();
                em.getTransaction().commit();

                response.setMessage("success");
                response.setResponseCode(0);

                return response;
            }
        } catch (Exception e) {
            response.setMessage("failure");
            response.setResponseCode(2);
            return response;
        } finally {
            em.clear();
        }

    }//end deletePublicPoi()

    public OtnServiceVerificationResponse addVerification(OtnServicePoiIdRequest request) {
        OtnServiceVerificationResponse response = new OtnServiceVerificationResponse();

        Poi poi;

        PublicPoi public_poi;

        TypedQuery query;

        User users;

        Apps app = null;

        PoiVerified poiVerified;

        PublicPoiVerified publicPoiVerified;

        try {

            users = em.find(User.class, request.getUserId());

            if (users == null) {
                response.setMessage("user does not exist");
                response.setResponseCode(1);
                return response;
            }

            //find poi
            em.getTransaction().begin();
            if (!request.isIs_public()) {
                //add verification to  poi
                //***********************find app name******************************
                if (request.getAppId() != 0) {
                    app = em.find(Apps.class, request.getAppId());
                    if (app == null) {
                        response.setMessage("application does not exist");
                        response.setResponseCode(1);
                        return response;
                    }
                }

                query = (TypedQuery) em.createQuery("SELECT p FROM Poi p WHERE p.appId = :appId and p.poiId = :poiId ");

                query.setParameter("appId", app);
                query.setParameter("poiId", request.getPoiId());

                if (query.getResultList().isEmpty()) {
                    response.setMessage("poi id does not exist for this app");
                    response.setResponseCode(1);
                    return response;
                } else {
                    poi = (Poi) query.getSingleResult();

                }

                query = (TypedQuery) em.createQuery("SELECT p FROM Poi p WHERE p.appId = :appId and p.poiId = :poiId and p.userId != :userId");

                query.setParameter("appId", app);
                query.setParameter("poiId", request.getPoiId());
                query.setParameter("userId", users);

                if (query.getResultList().isEmpty()) {
                    response.setResponseCode(501);
                    response.setMessage("the owner of the poi cannot verify the POI");
                    if (poi.getVerified() != null) {
                        response.setVerification(poi.getVerified());
                    }
                    return response;
                } else {
                    poi = (Poi) query.getSingleResult();
                }

                query = (TypedQuery) em.createQuery("SELECT p FROM PoiVerified p WHERE p.userId = :userId and p.poiId = :poiId");

                query.setParameter("userId", users);
                query.setParameter("poiId", poi);

                if (query.getResultList().isEmpty()) {
                    if (poi.getVerified() == null) {
                        poi.setVerified(0);
                    }
                    poi.setVerified(poi.getVerified() + 1);
                    em.merge(poi);

                    /**
                     * create new record to poi verified so this user cannot
                     * verify again the POI
                     */
                    response.setVerification(poi.getVerified());
                    response.setPoiId(poi.getPoiId());

                    poiVerified = new PoiVerified();
                    poiVerified.setPoiId(poi);
                    poiVerified.setUserId(users);

                    em.persist(poiVerified);
                    em.flush();
                    em.getTransaction().commit();

                    response.setMessage("success");
                    response.setResponseCode(0);
                } else {
                    response.setMessage("POI is already verified.");
                    response.setResponseCode(501);
                    response.setVerification(poi.getVerified());
                    response.setPoiId(poi.getPoiId());
                }
            } else {
                //add verification to public poi

                public_poi = em.find(PublicPoi.class, request.getPoiId());

                if (public_poi == null) {
                    response.setResponseCode(1);
                    response.setMessage("public poi id does not exist");
                    return response;
                }

                query = (TypedQuery) em.createQuery("SELECT p FROM PublicPoiVerified p WHERE p.userId = :userId and p.publicPoiId = :poiId");

                query.setParameter("userId", users);
                query.setParameter("poiId", public_poi);

                if (query.getResultList().isEmpty()) {
                    if (public_poi.getVerify() == null) {
                        public_poi.setVerify(0);
                    }

                    public_poi.setVerify(public_poi.getVerify() + 1);
                    em.merge(public_poi);

                    /**
                     * create new record to public poi verified so this user
                     * cannot verify again the POI
                     */
                    response.setVerification(public_poi.getVerify());
                    response.setPoiId(public_poi.getPublicPoiId());

                    publicPoiVerified = new PublicPoiVerified();
                    publicPoiVerified.setPublicPoiId(public_poi);
                    publicPoiVerified.setUserId(users);

                    em.persist(publicPoiVerified);
                    em.flush();
                    em.getTransaction().commit();

                    response.setMessage("success");
                    response.setResponseCode(0);
                } else {
                    response.setMessage("this user has already verified this POI");
                    response.setResponseCode(501);
                    response.setVerification(public_poi.getVerify());
                    response.setPoiId(public_poi.getPublicPoiId());

                }
            }

            return response;
        } catch (Exception e) {
            response.setMessage("failure");
            response.setResponseCode(2);
            return response;
        } finally {
            em.clear();
        }

    }//end addVerification()

    public OtnServiceVerificationResponse getVerification(int poiId, boolean is_public) {
        OtnServiceVerificationResponse response = new OtnServiceVerificationResponse();

        Poi poi;
        PublicPoi public_poi;
        try {

            //find poi
            em.getTransaction().begin();

            if (!is_public) {
                poi = em.find(Poi.class, poiId);

                if (poi == null) {
                    response.setResponseCode(1);
                    response.setMessage("poi id does not exist");
                    return response;

                }

                response.setVerification(poi.getVerified());
                response.setPoiId(poi.getPoiId());

                response.setMessage("success");
                response.setResponseCode(0);
            } else {

                public_poi = em.find(PublicPoi.class, poiId);

                if (public_poi == null) {
                    response.setResponseCode(1);
                    response.setMessage("poi id does not exist");
                    return response;

                }

                response.setVerification(public_poi.getVerify());
                response.setPoiId(public_poi.getPublicPoiId());

                response.setMessage("success");
                response.setResponseCode(0);

            }

            return response;
        } catch (Exception e) {

            response.setMessage("failure");
            response.setResponseCode(2);
            return response;
        } finally {
            em.clear();
        }

    }//end getVerification()

    public OtnServiceResponse addPoiRatings(OtnServicePoiRatingsRequest request) {
        OtnServiceResponse response = new OtnServiceResponse();

        List<PoiRating> poiRatingList;

        TypedQuery query;

        PoiRating poiRating;

        Poi poi;

        User user;

        Apps app;

        PoiRatingType poiRatingType;

        List<Poi> poiparamsList;

        List<PublicPoiRating> publicPoiRatingList;

        PublicPoiRating publicPoiRating;

        PublicPoi public_poi;

        //**********************Action****************************
        user = em.find(User.class, request.getUserId());

        if (user == null) {
            response.setResponseCode(1);
            response.setMessage("user does not exist");
            return response;

        }

        if (request.isIs_public() == null || !request.isIs_public()) {

            try {
                //find poi
                em.getTransaction().begin();
                poi = em.find(Poi.class, request.getPoiId());

                if (poi == null) {
                    response.setResponseCode(1);
                    response.setMessage("poi id does not exist");
                    return response;

                }
                app = em.find(Apps.class, request.getAppId());

                if (app == null) {

                    response.setMessage("application does not exist");
                    response.setResponseCode(1);
                    return response;
                }

                query = (TypedQuery) em.createQuery("SELECT p FROM Poi p WHERE p.appId = :appId and p.poiId = :poiId");

                query.setParameter("appId", app);

                query.setParameter("poiId", request.getPoiId());

                //we use list to avoid "not found" exception
                poiparamsList = query.getResultList();

                //if we found no results, the users has no poi with this poiId
                //so return error message
                if (poiparamsList.isEmpty()) {
                    response.setMessage("no available poi for this user");
                    response.setResponseCode(1);
                    return response;

                }

                OtnServiceAddPoiResponse rate_response = checkPoiRateAndType(request.getPoiRatings());

                if (rate_response.getResponseCode() == 0) {

                    for (OtnServicePoiRatings ratingList : request.getPoiRatings()) {
                        if (!em.getTransaction().isActive()) {
                            em.getTransaction().begin();
                        }

                        poiRatingType = em.find(PoiRatingType.class, ratingList.getRatingTypeId());

                        if (poiRatingType == null) {
                            response.setResponseCode(1);
                            response.setMessage("poi rating type does not exist");
                            return response;

                        }
                        query = (TypedQuery) em.createQuery("SELECT p FROM PoiRating p WHERE p.userId = :userId and "
                                + "p.poiId = :poiId and p.poiRatingTypeId = :poiRatingTypeId");

                        query.setParameter("poiId", poi);
                        query.setParameter("userId", user);
                        query.setParameter("poiRatingTypeId", poiRatingType);

                        //we use list to avoid "not found" exception
                        poiRatingList = query.getResultList();

                        if (poiRatingList.isEmpty()) {
                            poiRating = new PoiRating();

                            poiRating.setPoiId(poi);
                            poiRating.setRate(ratingList.getRate());
                            poiRating.setPoiRatingTypeId(poiRatingType);
                            poiRating.setUserId(user);
                            em.persist(poiRating);
                            em.flush();
                            em.getTransaction().commit();
                            em.clear();

                        } else {
                            poiRating = poiRatingList.get(0);

                            poiRating.setRate(ratingList.getRate());

                            em.merge(poiRating);
                            em.flush();
                            em.getTransaction().commit();
                        }
                    }

                } else {
                    response.setMessage(rate_response.getMessage());
                    response.setResponseCode(rate_response.getResponseCode());
                    return response;
                }

                response.setMessage("success");
                response.setResponseCode(0);

                return response;
            } catch (Exception e) {

                response.setMessage("failure");
                response.setResponseCode(2);
                return response;
            } finally {
                em.clear();
            }
            //if poi is public (true)
        } else {

            try {
                //find poi
                em.getTransaction().begin();
                public_poi = em.find(PublicPoi.class, request.getPoiId());

                if (public_poi == null) {
                    response.setResponseCode(1);
                    response.setMessage("poi id does not exist");
                    return response;
                }
                OtnServiceAddPoiResponse rate_response = checkPoiRateAndType(request.getPoiRatings());

                if (rate_response.getResponseCode() == 0) {

                    for (OtnServicePoiRatings ratingList : request.getPoiRatings()) {
                        if (!em.getTransaction().isActive()) {
                            em.getTransaction().begin();
                        }

                        poiRatingType = em.find(PoiRatingType.class, ratingList.getRatingTypeId());

                        if (poiRatingType == null) {
                            response.setResponseCode(1);
                            response.setMessage("poi rating type does not exist");
                            return response;
                        }
                        query = (TypedQuery) em.createQuery("SELECT p FROM PublicPoiRating p WHERE p.userId = :userId and "
                                + "p.publicPoiId = :poiId and p.poiRatingTypeId = :poiRatingTypeId");

                        query.setParameter("poiId", public_poi);
                        query.setParameter("userId", user);
                        query.setParameter("poiRatingTypeId", poiRatingType);

                        //we use list to avoid "not found" exception
                        publicPoiRatingList = query.getResultList();

                        if (publicPoiRatingList.isEmpty()) {
                            publicPoiRating = new PublicPoiRating();

                            publicPoiRating.setPublicPoiId(public_poi);
                            publicPoiRating.setRate(ratingList.getRate());
                            publicPoiRating.setPoiRatingTypeId(poiRatingType);
                            publicPoiRating.setUserId(user);
                            em.persist(publicPoiRating);
                            em.flush();
                            em.getTransaction().commit();
                            em.clear();
                        } else {
                            publicPoiRating = publicPoiRatingList.get(0);

                            publicPoiRating.setRate(ratingList.getRate());

                            em.merge(publicPoiRating);
                            em.flush();
                            em.getTransaction().commit();
                        }
                    }
                } else {
                    response.setMessage(rate_response.getMessage());
                    response.setResponseCode(rate_response.getResponseCode());
                    return response;
                }
                response.setMessage("success");
                response.setResponseCode(0);

                return response;
            } catch (Exception e) {
                response.setMessage("failure");
                response.setResponseCode(2);
                return response;
            } finally {
                em.clear();
            }

        }

    }//end addPOIRatings

    private OtnServiceAddPoiResponse checkPoiRateAndType(List<OtnServicePoiRatings> ratings) {
        OtnServiceAddPoiResponse response = new OtnServiceAddPoiResponse();

        try {
            for (OtnServicePoiRatings poiRatingList : ratings) {

                PoiRatingType poi_rating_type = em.find(PoiRatingType.class, poiRatingList.getRatingTypeId());
                if (poi_rating_type == null) {
                    response.setResponseCode(1);
                    response.setMessage("rating type does not exit");
                    return response;

                } else {
                    if (poiRatingList.getRate() >= 1 && poiRatingList.getRate() <= 5) {
                        response.setResponseCode(0);

                    } else {
                        response.setMessage("rating is out of range");
                        response.setResponseCode(1);
                        return response;

                    }
                }
            }

        } catch (Exception e) {
            return response;

        }
        return response;
    }

} //end class

