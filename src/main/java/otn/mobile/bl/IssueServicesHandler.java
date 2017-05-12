/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.bl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import otn.mobile.model.OtnServiceDeleteIssueTypeRequest;
import otn.mobile.model.OtnServiceGetIssueRequest;
import otn.mobile.model.OtnServiceGetIssueResponse;
import otn.mobile.model.OtnServiceIssueReportRequest;
import otn.mobile.model.OtnServiceIssueReportResponse;
import otn.mobile.model.OtnServiceLoadIssueListResponse;
import otn.mobile.model.OtnServiceLoadIssueRequest;
import otn.mobile.model.OtnServiceLoadIssueResponse;
import otn.mobile.model.OtnServiceLoadIssueTypeListResponse;
import otn.mobile.model.OtnServiceLoadIssueTypeResponse;
import otn.mobile.model.OtnServiceResponse;
import otn.mobile.model.OtnServiceUpdateIssueTypeRequest;
import otn.mobile.persistency.Apps;
import otn.mobile.persistency.Issue;
import otn.mobile.persistency.IssueType;
import otn.mobile.persistency.Track;
import otn.mobile.persistency.User;

/**
 *
 * @author EMantziou
 */
public class IssueServicesHandler {

    private EntityManager em;

    public IssueServicesHandler(EntityManager em) {
        this.em = em;

    }

    public OtnServiceIssueReportResponse issueReportByTrack(OtnServiceIssueReportRequest request) {

        OtnServiceIssueReportResponse response = new OtnServiceIssueReportResponse();
        User users;
        Track track;
        Issue issue;

        TypedQuery query;
        List<Track> tracksparamsList;
        try {
            System.out.println("***********Issue report*******************");

            if (request.getUserId() != null || !request.getUserId().isEmpty()) {

                users = em.find(User.class, request.getUserId());

                if (users == null) {

                    response.setMessage("no user with this id");
                    response.setResponseCode(1);
                    return response;
                } else {

                    query = (TypedQuery) em.createQuery("SELECT t FROM Track t WHERE t.userId = :userId and t.trackId= :trackId");

                    query.setParameter("userId", users);

                    query.setParameter("trackId", request.getTrackId());

                    //we use list to avoid "not found" exception
                    tracksparamsList = query.getResultList();

                    //if we found no results, the users is not registered 
                    //so return error message
                    if (tracksparamsList.isEmpty()) {
                        response.setMessage("no available tracks for this user");
                        response.setResponseCode(1);
                        return response;

                    } else {

                        track = tracksparamsList.get(0);

                    }
                }

            } else {

                track = em.find(Track.class, request.getTrackId());
            }

            if (track != null) {

                em.getTransaction().begin();
                issue = new Issue();
                issue.setName(request.getName());
                issue.setDescription(request.getDescription());
//                issue.setPicture(request.getPicture());
                issue.setReportDate(request.getDatetime());
//                issue.setIssueTypeInt(null);
                issue.setTrackId(track);
                issue.setLatitude(request.getLatitude());
                issue.setLongitude(request.getLongitude());
                em.persist(issue);
                em.flush();
                em.getTransaction().commit();
                em.clear();

                response.setMessage("issue reported successfully  ");
                response.setResponseCode(0);
                response.setIssueId(issue.getIssueId());
                return response;

            } else {

                response.setMessage("there is no track with this track id");
                response.setResponseCode(1);
                return response;
            }
        } catch (Exception e) {

//            e.printStackTrace();
            response.setMessage("failure");
            response.setResponseCode(2);
            return response;
        } finally {
            em.clear();
        }

    }//end issueReport by issueType

    public OtnServiceIssueReportResponse issueReport(OtnServiceIssueReportRequest request) {

        OtnServiceIssueReportResponse response = new OtnServiceIssueReportResponse();
        User users;
        Track track = null;
        Apps app = null;
        Issue issue;
        IssueType issueType;

        TypedQuery query;
        List<Track> tracksparamsList;
        try {
            System.out.println("***********Issue report*******************");
            issue = new Issue();
            if (request.getUserId() != null) {
                if (!request.getUserId().isEmpty()) {

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
                        issue.setUserId(users);
                        System.out.println("user created");
                    } else {
                        issue.setUserId(users);

                    }

                    app = em.find(Apps.class, request.getAppId());
//                    app = em.find(Apps.class, 40);

                    if (app == null) {

                        response.setMessage("application does not exist");
                        response.setResponseCode(1);
                        return response;
                    }

                    query = (TypedQuery) em.createQuery("SELECT t FROM Track t WHERE t.userId = :userId and t.trackId= :trackId");

                    query.setParameter("userId", users);

                    query.setParameter("trackId", request.getTrackId());

                    //we use list to avoid "not found" exception
                    tracksparamsList = query.getResultList();

                    //if we found no results, the users is not registered 
                    //so return error message
                    if (tracksparamsList.isEmpty()) {
                        System.out.println("no available tracks for this user");
//                        response.setMessage("no available tracks for this user");
//                        response.setResponseCode(1);
//                        return response;

                    } else {

                        track = tracksparamsList.get(0);

                    }
                } else {
                    if (request.getTrackId() != 0) {
                        track = em.find(Track.class, request.getTrackId());
                    }
                }
            } else {
                if (request.getTrackId() != 0) {
                    track = em.find(Track.class, request.getTrackId());
                }
            }

            em.getTransaction().begin();

            if (request.getIssueTypeId() != 0) {
                issueType = em.find(IssueType.class, request.getIssueTypeId());
                if (issueType != null) {
                    issue.setIssueTypeInt(issueType);
                } else {
                    response.setMessage("there is no issue type with this id");
                    response.setResponseCode(1);
                    return response;

                }
            } else {
                response.setMessage("no available issues");
                response.setResponseCode(1);
                return response;
            }
            if (request.getName() != null) {
                issue.setName(request.getName());
            } else {
                issue.setName("");
            }
            issue.setDescription(request.getDescription());
            issue.setPicture(request.getPicture());
            issue.setReportDate(request.getDatetime());
            issue.setAppId(app);

            if (request.getTrackId() != 0) {
                issue.setTrackId(track);
            }
            issue.setLatitude(request.getLatitude());
            issue.setLongitude(request.getLongitude());
            em.persist(issue);
            em.flush();
            em.getTransaction().commit();
            em.clear();

            response.setMessage("issue reported successfully  ");
            response.setResponseCode(0);
            response.setIssueId(issue.getIssueId());
            return response;

        } catch (Exception e) {

//            e.printStackTrace();
            response.setMessage("failure");
            response.setResponseCode(2);

            return response;
        } finally {
            em.clear();
        }

    }//end issueReport

    public OtnServiceLoadIssueResponse loadIssues(OtnServiceLoadIssueRequest request) {

        //*********************** Variables ***************************
        OtnServiceLoadIssueResponse response = new OtnServiceLoadIssueResponse();

        User users = null;

        String queryString;

        TypedQuery query;

        IssueType issueType = null;

        Apps app = null;

        List<Issue> issueparamsList;

        ArrayList<OtnServiceLoadIssueListResponse> issueList;

        StringBuilder sb = new StringBuilder();

        //************************* Action ****************************
        System.out.println("------------load issues---------------");

        try {
            //**********************find user*****************************
            if (request.getIssueTypeId() != 0 && request.getAppId() == 0) {

                issueType = em.find(IssueType.class, request.getIssueTypeId());

                if (issueType == null) {
                    response.setMessage("issue type does not exist");
                    response.setResponseCode(1);
                    return response;
                }

                //***************find issues by issue type id****************************
//            query = (TypedQuery) em.createQuery("SELECT i FROM Issue i WHERE i.issueTypeInt = :issueTypeId ");
//            query.setParameter("issueTypeId", issueType);
                queryString = "SELECT i FROM Issue i WHERE i.issueTypeInt = :issueTypeId ";
                sb.append(queryString);

                // find issues by appId
            } else {
                app = em.find(Apps.class, request.getAppId());
                queryString = "SELECT i FROM Issue i WHERE i.appId = :appId ";
                sb.append(queryString);
            }
            if (request.getUserId() != null && !request.getUserId().isEmpty()) {
                users = em.find(User.class, request.getUserId());
                sb.append(" and i.userId = :userId");
                if (users == null) {
                    response.setMessage("user does not exist");
                    response.setResponseCode(1);
                    return response;
                }
            }

            query = em.createQuery(sb.toString(), Issue.class);
            if (request.getIssueTypeId() != 0 && request.getAppId() == 0 && issueType != null) {
                query.setParameter("issueTypeId", issueType);
            } else if (app != null) {
                query.setParameter("appId", app);
            }
            if (request.getUserId() != null && !request.getUserId().isEmpty()) {
                query.setParameter("userId", users);
            }

            //we use list to avoid "not found" exception
            issueparamsList = query.getResultList();

            //if we found no results, the users has no issueType with this trackId
            //so return error message
            if (issueparamsList.isEmpty()) {
                response.setMessage("no available track for this user");
                response.setResponseCode(1);
                return response;

            } else {
                response.setMessage("successful loading");
                response.setResponseCode(0);
                issueList = new ArrayList<OtnServiceLoadIssueListResponse>();
                for (Issue issue : issueparamsList) {

//                    issueType = em.find(IssueType.class, issue.getIssueTypeInt().getIssueTypeId());
//                    System.out.println("issue type name " + issueType.getName());
                    if (users == null) {
                        users = em.find(User.class, issue.getUserId().getUserId());
                    }

                    int appId = 0;
                    if (issue.getAppId() != null) {
                        appId = issue.getAppId().getAppId();
                    }

                    issueList.add(new OtnServiceLoadIssueListResponse(issue.getIssueId(), issue.getName(),
                            issue.getDescription(), issue.getPicture(), issue.getReportDate(), issue.getIssueTypeInt().getName(), issue.getLatitude(),
                            issue.getLongitude(), users.getUserId(), appId));
                }
                response.setIssueList(issueList);

            }

        } catch (Exception e) {
//            e.printStackTrace();
            response.setMessage("failure");
            response.setResponseCode(2);
        } finally {

            return response;
        }

    }//end loadIssues()

    public OtnServiceGetIssueResponse loadIssue(OtnServiceGetIssueRequest request) {

        //*********************** Variables ***************************
        OtnServiceGetIssueResponse response = new OtnServiceGetIssueResponse();
        Issue issue;

        User users = null;

        String queryString;

        TypedQuery query;

        Apps app = null;
        
        List<Issue> issueparamsList;

        StringBuilder sb = new StringBuilder();

        //************************* Action ****************************
        System.out.println("------------load issue---------------");

        try {
            //**********************find issue by issue id*****************************
            //            query = (TypedQuery) em.createQuery("SELECT i FROM Issue i WHERE i.issueTypeInt = :issueTypeId ");
//            query.setParameter("issueTypeId", issueType);
            queryString = "SELECT i FROM Issue i WHERE i.issueId = :issueId ";
            sb.append(queryString);

            if (request.getAppId() != 0) {

                // find issues by appId
                app = em.find(Apps.class, request.getAppId());
                if (app == null) {
                    response.setMessage("app does not exist");
                    response.setResponseCode(1);
                    return response;
                }
                queryString = " and i.appId = :appId ";
                sb.append(queryString);
            }
            if (request.getUserId() != null && !request.getUserId().isEmpty()) {
                users = em.find(User.class, request.getUserId());
                sb.append(" and i.userId = :userId");
                if (users == null) {
                    response.setMessage("user does not exist");
                    response.setResponseCode(1);
                    return response;
                }
            }

//              issue = em.find(Issue.class, request.getIssueId());
            query = em.createQuery(sb.toString(), Issue.class);

            query.setParameter("issueId", request.getIssueId());
            if (app != null) {
                query.setParameter("appId", app);
            }
            if (users!=null) {
                query.setParameter("userId", users);
            }

//          issueparamsList = query.getResultList();

        
            if (query.getResultList().isEmpty()) {
                response.setMessage("issue does not exist or does not belong to this user or app");
                response.setResponseCode(1);
                return response;
            } else {
               issue =(Issue) query.getSingleResult();
                response.setMessage("successful loading");
                response.setResponseCode(0);

                int appId = 0;
                if (issue.getAppId() != null) {
                    appId = issue.getAppId().getAppId();
                }

                response.setAppId(appId);
                response.setDescription(issue.getDescription());
                response.setIssueId(issue.getIssueId());
                response.setIssueTypeName(issue.getIssueTypeInt().getName());
                response.setLatitude(issue.getLatitude());
                response.setLongitude(issue.getLongitude());
                response.setName(issue.getName());
                response.setPicture(issue.getPicture());
                response.setReport_date(issue.getReportDate());
                response.setUserId(issue.getUserId().getUserId());
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("failure");
            response.setResponseCode(2);
        } finally {
            return response;
        }

    }//end loadIssue

    public OtnServiceResponse addIssueType(OtnServiceUpdateIssueTypeRequest request) {

        //*********************** Variables ***************************
        OtnServiceResponse response = new OtnServiceResponse();

        IssueType issueType;

        Apps app;

        TypedQuery query;
        //************************* Action ****************************
        System.out.println("------------Start---------------");

        try {

            //***************find the app****************************
            app = em.find(Apps.class, request.getAppId());
            if (app == null) {
                response.setMessage("application does not exist");
                response.setResponseCode(1);
                return response;
            }

            query = (TypedQuery) em.createQuery("SELECT i FROM IssueType i WHERE i.issueTypeId = :issueTypeId and i.appId = :appId");

            query.setParameter("issueTypeId", request.getIssueTypeId());
            query.setParameter("appId", app);

            //we use list to avoid "not found" exception
            if (!query.getResultList().isEmpty()) {
                response.setMessage("issue type id already exists in this app");
                response.setResponseCode(1);
                return response;

            }

            issueType = em.find(IssueType.class, request.getIssueTypeId());

            if (issueType != null) {
                response.setMessage("issue type id already exists");
                response.setResponseCode(1);
                return response;
            }

            //***************add issueType record****************************
            em.getTransaction().begin();

            issueType = new IssueType();

            issueType.setIssueTypeId(request.getIssueTypeId());
            issueType.setName(request.getName());
            issueType.setAppId(app);

            em.persist(issueType);
            em.flush();
            em.getTransaction().commit();
            em.clear();

            response.setMessage("success");
            response.setResponseCode(0);

            return response;

        } catch (Exception e) {
//            e.printStackTrace();

            response.setMessage("failure");
            response.setResponseCode(2);

            return response;
        } finally {
            em.clear();

        }

    }//end updateIssueType()

    public OtnServiceResponse updateIssueType(OtnServiceUpdateIssueTypeRequest request) {

        //*********************** Variables ***************************
        OtnServiceResponse response = new OtnServiceResponse();

        IssueType issueType;
        Apps app;

        //************************* Action ****************************
        System.out.println("------------Start---------------");

        try {

            //***************find the app****************************
            app = em.find(Apps.class, request.getAppId());
            if (app == null) {
                response.setMessage("application does not exist");
                response.setResponseCode(1);
                return response;
            }

            //***************find the issueType record****************************
            issueType = em.find(IssueType.class, request.getIssueTypeId());

            if (issueType == null) {
                response.setMessage("issue type does not exist");
                response.setResponseCode(1);
                return response;
            } else {

                //***************Update issueType record****************************
                em.getTransaction().begin();
                if (request.getName() != null) {
                    issueType.setName(request.getName());
                }
                issueType.setAppId(app);
                em.merge(issueType);
                em.flush();
                em.getTransaction().commit();

                response.setMessage("success");
                response.setResponseCode(0);
            }
            return response;

        } catch (Exception e) {
//            e.printStackTrace();

            response.setMessage("failure");
            response.setResponseCode(2);
            return response;
        } finally {
            em.clear();

        }

    }//end updateIssueType()

    public OtnServiceLoadIssueTypeResponse getIssueType(OtnServiceUpdateIssueTypeRequest request) {

        //*********************** Variables ***************************
        OtnServiceLoadIssueTypeResponse response = new OtnServiceLoadIssueTypeResponse();

        IssueType issueType;

        //************************* Action ****************************
        System.out.println("------------get issue type---------------");

        try {
            //**********************find user*****************************

            issueType = em.find(IssueType.class, request.getIssueTypeId());

            if (issueType == null) {
                response.setMessage("issue type does not exist");
                response.setResponseCode(1);
                return response;
            }

            response.setIssueTypeId(issueType.getIssueTypeId());
            response.setName(issueType.getName());
            response.setAppId(issueType.getAppId().getAppId());
            response.setMessage("successful loading");
            response.setResponseCode(0);
            return response;
        } catch (Exception e) {
//            e.printStackTrace();
            response.setMessage("failure");
            response.setResponseCode(2);
            return response;
        }

    }//end getIssueType()

    public OtnServiceLoadIssueTypeListResponse getIssueTypes(OtnServiceUpdateIssueTypeRequest request) {

        //*********************** Variables ***************************
        OtnServiceLoadIssueTypeListResponse response = new OtnServiceLoadIssueTypeListResponse();

        Apps app;

        TypedQuery query;

        List<IssueType> issuetypeparamsList;

        ArrayList<OtnServiceLoadIssueTypeResponse> issueTypeList;

        //************************* Action ****************************
        System.out.println("------------load issues---------------");

        try {

            //***************find the app****************************
            app = em.find(Apps.class, request.getAppId());
            if (app == null) {
                response.setMessage("application does not exist");
                response.setResponseCode(1);
                return response;
            }
            //***************find issues types by app id****************************

            query = (TypedQuery) em.createQuery("SELECT i FROM IssueType i WHERE i.appId = :appId");
            query.setParameter("appId", app);

            //we use list to avoid "not found" exception
            issuetypeparamsList = query.getResultList();

            //if we found no results, the users has no issueType with this trackId
            //so return error message
            if (issuetypeparamsList.isEmpty()) {
                response.setMessage("no available issues for this app");
                response.setResponseCode(1);
                return response;

            } else {
                response.setMessage("successful loading");
                response.setResponseCode(0);
                issueTypeList = new ArrayList<OtnServiceLoadIssueTypeResponse>();
                for (IssueType issuetype : issuetypeparamsList) {

//                    issueType = em.find(IssueType.class, issue.getIssueTypeInt().getIssueTypeId());
                    System.out.println("issue type name " + issuetype.getName());

                    issueTypeList.add(new OtnServiceLoadIssueTypeResponse(issuetype.getIssueTypeId(), issuetype.getName(), issuetype.getAppId().getAppId()));
                }
                response.setIssueTypeList(issueTypeList);

            }

        } catch (Exception e) {
//            e.printStackTrace();
            response.setMessage("failure");
            response.setResponseCode(2);
        } finally {

            return response;
        }

    }//end loadPublicTracks()

    public OtnServiceResponse deleteIssueType(OtnServiceDeleteIssueTypeRequest request) {
        OtnServiceResponse response = new OtnServiceResponse();

        IssueType issueType;

        try {
            em.getTransaction().begin();
            issueType = em.find(IssueType.class, request.getIssueTypeId());

            if (issueType == null) {
                response.setMessage("issue type does not exist");
                response.setResponseCode(1);
                return response;
            } else {

                em.remove(issueType);
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

    }//end deleteIssueType()

}// end class
