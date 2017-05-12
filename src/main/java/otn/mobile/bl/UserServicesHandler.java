/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.bl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import otn.mobile.model.OtnServiceAddUserRequest;
import otn.mobile.model.OtnServiceDeleteUserContentRequest;
import otn.mobile.model.OtnServiceResponse;
import otn.mobile.persistency.Apps;
import otn.mobile.persistency.Issue;
import otn.mobile.persistency.Poi;
import otn.mobile.persistency.PoiRating;
import otn.mobile.persistency.PoiVerified;
import otn.mobile.persistency.Track;
import otn.mobile.persistency.TrackRating;
import otn.mobile.persistency.TrackVerified;
import otn.mobile.persistency.User;

/**
 *
 * @author EMantziou
 */
public class UserServicesHandler {

    EntityManager em;

    public UserServicesHandler(EntityManager em) {
        this.em = em;
    }

    public OtnServiceResponse addUser(OtnServiceAddUserRequest request) {
        OtnServiceResponse response = new OtnServiceResponse();
        User user;
        
        Apps app;
        try {
            user = em.find(User.class, request.getUserId());

            if (user != null) {
//                System.out.println("=====================================");
//                System.out.println("user id " + user.getUserId());
                response.setMessage("user already exists");
                response.setResponseCode(1);
                return response;

            } else {
                em.getTransaction().begin();

                user = new User();
                user.setUserId(request.getUserId());

                if (request.getAppId() != 0) {
                    app = em.find(Apps.class, request.getAppId());
                    if (app == null) {
                        response.setMessage("application does not exist");
                        response.setResponseCode(1);
                        return response;
                    } else {
                        user.setAppId(app);
                    }
                }

                em.persist(user);
                em.flush();
                em.getTransaction().commit();
                em.clear();

                response.setMessage("success");
                response.setResponseCode(0);
                return response;

            }

        } catch (Exception e) {
            e.printStackTrace();

            response.setMessage("failure");
            response.setResponseCode(2);

        } finally {

            return response;
        }

    }//end AddUser

    public OtnServiceResponse deleteUserContent(OtnServiceDeleteUserContentRequest request) {
        //*********************** Variables ***************************
        OtnServiceResponse response = new OtnServiceResponse();

        User users;

        Apps app;

        TypedQuery query;

        List<Track> tracksparamsList;
        List<TrackRating> trackRatingList;
        List<TrackVerified> trackVerifiedList;

        List<Poi> poiparamsList;
        List<PoiRating> poiRatingList;
        List<PoiVerified> poiVerifiedList;

        List<Issue> issueparamsList;

        //*********************** Action ***************************
        users = em.find(User.class, request.getUserId());

        if (users == null) {
            response.setMessage("user does not exist");
            response.setResponseCode(1);
            return response;
        }

        app = em.find(Apps.class, request.getAppId());

        if (app == null) {
            response.setMessage("application does not exist");
            response.setResponseCode(1);
            return response;
        }
        try {
            em.getTransaction().begin();

            //**************remove user's track per application************************
            System.out.println("*********find tracks from users and apps");
            query = (TypedQuery) em.createQuery("SELECT t FROM Track t WHERE t.userId = :userId and t.appId = :appId");
            query.setParameter("userId", users);
            query.setParameter("appId", app);

            //we use list to avoid "not found" exception
            tracksparamsList = query.getResultList();

            //if we found no results, the users is not registered 
            //so return error message
            if (tracksparamsList.isEmpty()) {
                System.out.println("no available tracks for this user");

            } else {
                System.out.println("tracks found");
                System.out.println("search for track ratings ");

                //**************remove user's track ratings *********************
                query = (TypedQuery) em.createQuery("SELECT t FROM TrackRating t WHERE t.userId = :userId and t.trackId IN :tracksparamsList");
                query.setParameter("userId", users);
                query.setParameter("tracksparamsList", tracksparamsList);

                //we use list to avoid "not found" exception
                trackRatingList = query.getResultList();

                if (trackRatingList.isEmpty()) {
                    System.out.println("user never rate tracks");

                } else {

                    for (TrackRating tr : trackRatingList) {
                        if (!em.getTransaction().isActive()) {
                            em.getTransaction().begin();
                        }

                        em.remove(tr);
                        em.flush();
                        em.getTransaction().commit();
                    }

                    System.out.println("track ratings found and deleted");
                }

                System.out.println("search for track verification ");
                //**************remove user's track verification************************
                query = (TypedQuery) em.createQuery("SELECT t FROM TrackVerified t WHERE t.userId = :userId and t.trackId IN :tracksparamsList");
                query.setParameter("userId", users);
                query.setParameter("tracksparamsList", tracksparamsList);

                trackVerifiedList = query.getResultList();

                if (trackVerifiedList.isEmpty()) {
                    System.out.println("user never verified tracks");

                } else {
                    System.out.println("track verification found and deleted");
                    for (TrackVerified tr : trackVerifiedList) {
                        if (!em.getTransaction().isActive()) {
                            em.getTransaction().begin();
                        }

                        em.remove(tr);
                        em.flush();
                        em.getTransaction().commit();
                    }
                }

                System.out.println("*********find issues from users");
                //**************remove user's issues per application************************
                query = (TypedQuery) em.createQuery("SELECT i FROM Issue i WHERE  i.userId = :userId ");

                query.setParameter("userId", users);

                //we use list to avoid "not found" exception
                issueparamsList = query.getResultList();

                if (issueparamsList.isEmpty()) {
                    response.setMessage("no available issues for this user");
                } else {
                    System.out.println("issue found and deleted");

                    for (Issue i : issueparamsList) {
                        if (!em.getTransaction().isActive()) {
                            em.getTransaction().begin();
                        }

                        em.remove(i);
                        em.flush();
                        em.getTransaction().commit();
                    }
                }

                //remove tracks
                for (Track tr : tracksparamsList) {
                    if (!em.getTransaction().isActive()) {
                        em.getTransaction().begin();
                    }

                    em.remove(tr);
                    em.flush();
                    em.getTransaction().commit();
                }

            }
            System.out.println("*********find pois from users and apps");
            //**************remove user's poi per application************************
            query = (TypedQuery) em.createQuery("SELECT p FROM Poi p WHERE p.userId = :userId and p.appId = :appId");

            query.setParameter("userId", users);
            query.setParameter("appId", app);

            //we use list to avoid "not found" exception
            poiparamsList = query.getResultList();

            //if we found no results, the users has no poi with this trackId
            //so return error message
            if (poiparamsList.isEmpty()) {
                response.setMessage("no available poi for this user");
            } else {
                System.out.println("pois found");
                System.out.println("search for poi ratings ");

                //**************remove user's poi ratings *********************
                query = (TypedQuery) em.createQuery("SELECT p FROM PoiRating p WHERE p.userId = :userId and p.poiId IN :poiparamsList");
                query.setParameter("userId", users);
                query.setParameter("poiparamsList", poiparamsList);

                //we use list to avoid "not found" exception
                poiRatingList = query.getResultList();

                if (poiRatingList.isEmpty()) {
                    System.out.println("user never rate pois");

                } else {
                    System.out.println("poi ratings found and deleted");

                    for (PoiRating pr : poiRatingList) {
                        if (!em.getTransaction().isActive()) {
                            em.getTransaction().begin();
                        }

                        em.remove(pr);
                        em.flush();
                        em.getTransaction().commit();
                    }
                }
                System.out.println("search for poi verification ");
                //**************remove user's poi verification************************
                query = (TypedQuery) em.createQuery("SELECT p FROM PoiVerified p WHERE p.userId = :userId and p.poiId IN :poiparamsList");
                query.setParameter("userId", users);
                query.setParameter("poiparamsList", poiparamsList);

                poiVerifiedList = query.getResultList();

                if (poiVerifiedList.isEmpty()) {
                    System.out.println("user never verified pois");

                } else {
                    System.out.println("poi verification found and deleted");

                    for (PoiVerified pv : poiVerifiedList) {
                        if (!em.getTransaction().isActive()) {
                            em.getTransaction().begin();
                        }

                        em.remove(pv);
                        em.flush();
                        em.getTransaction().commit();
                    }

                }

                //remove POIs
                for (Poi p : poiparamsList) {
                    if (!em.getTransaction().isActive()) {
                        em.getTransaction().begin();
                    }

                    em.remove(p);
                    em.flush();
                    em.getTransaction().commit();
                }

            }

            response.setMessage("success");
            response.setResponseCode(0);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("failure");
            response.setResponseCode(2);
            return response;
        } finally {
            em.clear();
        }

    }

}//end class
