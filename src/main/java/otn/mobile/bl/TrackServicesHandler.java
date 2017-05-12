/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.bl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.StringReader;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.postgis.PGgeometry;
import org.postgis.Point;
import otn.mobile.model.OtnServiceResponse;
import otn.mobile.model.OtnServiceDeleteTrackRequest;
import otn.mobile.model.OtnServiceGeometryPoints;
import otn.mobile.model.OtnServiceGetTracksListResponse;
import otn.mobile.model.OtnServiceGetTracksRequest;
import otn.mobile.model.OtnServiceGetTracksResponse;
import otn.mobile.model.OtnServiceLoadPublicTracksResponse;
import otn.mobile.model.OtnServiceLoadTrackRequest;
import otn.mobile.model.OtnServiceLoadTrackResponse;
import otn.mobile.model.OtnServicePoiAvgRatings;
import otn.mobile.model.OtnServicePoiRatings;
import otn.mobile.model.OtnServiceStatisticsResponse;
import otn.mobile.model.OtnServiceSubTracksRequest;
import otn.mobile.model.OtnServiceSubTracksResponse;
import otn.mobile.model.OtnServiceTrackRatingsRequest;
import otn.mobile.model.OtnServiceTrackRequest;
import otn.mobile.model.OtnServiceTrackResponse;
import otn.mobile.model.OtnServiceTrackVerificationRequest;
import otn.mobile.model.OtnServiceTrackVerificationResponse;
import otn.mobile.model.OtnServiceWeatherRatings;
import otn.mobile.model.OtnServicesLoadPublicTracksListResponse;
import otn.mobile.model.OtnServicesMyTracksListResponse;
import otn.mobile.model.OtnServicesMyTracksRequest;
import otn.mobile.model.OtnServicesMyTracksResponse;
import otn.mobile.persistency.Apps;
import otn.mobile.persistency.Poi;
import otn.mobile.persistency.PoiVerified;
import otn.mobile.persistency.PublicPoi;
import otn.mobile.persistency.PublicPoiVerified;
import otn.mobile.persistency.Source;
import otn.mobile.persistency.Track;
import otn.mobile.persistency.TrackRating;
import otn.mobile.persistency.TrackRatingType;
import otn.mobile.persistency.TrackVerified;
import otn.mobile.persistency.TransportType;
import otn.mobile.persistency.User;
import otn.mobile.persistency.Weather;
import otn.mobile.persistency.WeatherType;
import otn.mobile.persistency.WebAnalytics;
import otn.mobile.services.trackServices;

/**
 *
 * @author EMantziou
 */
public class TrackServicesHandler {

    private EntityManager em;

    /*
     * development server
     */
    private String username = "username";
    private String password = "password";
    private String Base_url = "local_path";

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TrackServicesHandler.class.getName());

    public TrackServicesHandler(EntityManager em) {
        this.em = em;

    }

    public OtnServiceTrackResponse addTracks(OtnServiceTrackRequest request) {

        //*********************** Variables ***************************
        OtnServiceTrackResponse response = new OtnServiceTrackResponse();
        User users;

        TransportType transport_type = null;

        Weather weather;

        WeatherType weather_type;

        Apps app = null;

        Source source = null;

        Track track = new Track();

        Track subTracks;

        TrackRating track_rating;

        TrackRatingType track_rating_type;

        ArrayList<OtnServiceSubTracksResponse> trackList = new ArrayList<OtnServiceSubTracksResponse>();

        ArrayList<OtnServiceWeatherRatings> weatherListparams = null;

        ArrayList<OtnServiceGeometryPoints> geometryPointsListparams = null;

        byte[] trackFileCsv = null;
        //************************* Action ****************************
        System.out.println("------------Start---------------");

        try {
            //**********************find user*****************************

            users = em.find(User.class, request.getUserId());

            if (users != null) {

                System.out.println("=====================================");
                System.out.println("user id " + users.getUserId());

            } else {
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
            }

            //***********************find transportID******************************
            if (request.getTransportId() != 0) {

                transport_type = em.find(TransportType.class, request.getTransportId());

                if (transport_type != null) {
                    System.out.println("=====================================");
                    System.out.println("transport id " + transport_type.getTransportTypeId());
                    track.setTransportTypeId(transport_type);
                } else {
                    response.setMessage("transport type id does not exist");
                    response.setResponseCode(1);
                    return response;
                }
            }
            //***********************find weatherID******************************
//            if (request.getWeatherId() != 0) {
//                weather = em.find(WeatherType.class, request.getWeatherId());
//
//                if (weather != null) {
//                    System.out.println("=====================================");
//                    System.out.println("weather id " + weather.getWeatherId());
//                   
//                } else {
//                    response.setMessage("weather condition id does not exist");
//                    response.setResponseCode(1);
//                    return response;
//                }
//            }

            //***********************find app name******************************
            if (request.getAppId() != 0) {
                app = em.find(Apps.class, request.getAppId());

                if (app != null) {
                    System.out.println("=====================================");
                    System.out.println("app id " + app.getName());
                    track.setAppId(app);
                } else {
                    response.setMessage("application does not exist");
                    response.setResponseCode(1);
                    return response;
                }
            }
            //***********************find sourceId******************************
            if (request.getSourceId() != 0) {
                source = em.find(Source.class, request.getSourceId());

                if (source != null) {
                    System.out.println("=====================================");
                    System.out.println("source id " + source.getName());
                    track.setRouteSourceId(source);
                } else {
                    response.setMessage("source type id does not exist");
                    response.setResponseCode(1);
                    return response;
                }
            }
            //***************Create track record****************************
            em.getTransaction().begin();

            if (request.getName() != null) {
                track.setName(request.getName());
            } else {
                track.setName("");
            }

            track.setDescription(request.getDescription());
            if (request.getPicture() != null) {
                track.setPicture(request.getPicture());
            }
            track.setDistance(request.getDistance());
            track.setDuration(request.getDuration());

            track.setSpeed(request.getSpeed());
            track.setElevation(request.getElevation());

            if (request.getTrackFileCsv() != null) {

//                String csvFile = new String(request.getTrackFileCsv());
                trackFileCsv = Base64.encodeBase64(request.getTrackFileCsv());
                track.setTrackFileCsv(trackFileCsv);
            }

            track.setRouteKlm(request.getRoute_kml());
            track.setLatStart(request.getLat_start());
            track.setLongStart(request.getLon_start());
            track.setLatEnd(request.getLat_end());
            track.setLongEnd(request.getLon_end());
            track.setCreationDate(new Date());
            track.setDatetimeStart(request.getDatetime_start());
            track.setDatetimeEnd(request.getDatetime_end());
            track.setUserId(users);
            track.setIsPublic(request.isIs_public());
            track.setStartAddress(request.getStart_address());
            track.setEndAddress(request.getEnd_address());

            if (request.getTrackRatings().size() > 0) {

                OtnServiceTrackResponse rate_response = checkTrackRateAndType(request.getTrackRatings());

                if (rate_response.getResponseCode() == 0) {
                    em.persist(track);
                    em.flush();
                    em.getTransaction().commit();
                    em.clear();

                    for (OtnServicePoiRatings trackRatingList : request.getTrackRatings()) {
                        em.getTransaction().begin();
                        track_rating = new TrackRating();
                        track_rating_type = em.find(TrackRatingType.class, trackRatingList.getRatingTypeId());
                        track_rating.setTrackId(track);
                        track_rating.setTrackRatingTypeId(track_rating_type);
                        track_rating.setRate(trackRatingList.getRate());
                        track_rating.setUserId(users);

                        em.persist(track_rating);
                        em.flush();
                        em.getTransaction().commit();
                        em.clear();

                        response.setMessage("success");
                        response.setResponseCode(0);

                    }

                } else {

                    response.setMessage(rate_response.getMessage());
                    response.setResponseCode(rate_response.getResponseCode());
                    return response;
                }

            } else {
                em.persist(track);
                em.flush();
                em.getTransaction().commit();
                em.clear();

                response.setMessage("success");
                response.setResponseCode(0);

            }

            //***********************find weatherID******************************
            if (request.getWeatherList().size() > 0) {

                if (track.getTrackId() == 0) {
                    em.persist(track);
                    em.flush();
                    em.getTransaction().commit();
                    em.clear();
                }
                weatherListparams = new ArrayList<OtnServiceWeatherRatings>();
                for (OtnServiceWeatherRatings weatherList : request.getWeatherList()) {
                    em.getTransaction().begin();

                    weather = new Weather();
                    weather_type = em.find(WeatherType.class, weatherList.getWeatherTypeId());

                    if (weather_type == null) {
                        response.setMessage("weather id  does not exist");
                        response.setResponseCode(1);
                        return response;

                    }

                    weather.setTrackId(track);
                    weather.setWeatherTypeId(weather_type);

                    em.persist(weather);
                    em.flush();
                    em.getTransaction().commit();
                    em.clear();

                    response.setMessage("success");
                    response.setResponseCode(0);

                    weatherListparams.add(new OtnServiceWeatherRatings(weather_type.getWeatherId()));

                }
                response.setWeatherList(weatherListparams);

            }

            //***********************insert geometry points******************************
            // insert startPoint
            List<OtnServiceGeometryPoints> startPointsList = new ArrayList<OtnServiceGeometryPoints>();
            OtnServiceGeometryPoints startPoints = new OtnServiceGeometryPoints();
            startPoints.setLatitude(request.getLat_start());
            startPoints.setLongitude(request.getLon_start());
            startPointsList.add(startPoints);

            // insert endPoint
            List<OtnServiceGeometryPoints> endPointsList = new ArrayList<OtnServiceGeometryPoints>();
            OtnServiceGeometryPoints endPoints = new OtnServiceGeometryPoints();
            endPoints.setLatitude(request.getLat_end());
            endPoints.setLongitude(request.getLon_end());
            endPointsList.add(endPoints);

            /**
             * decode csv String
             */
            if (request.getTrackFileCsv() != null) {
                String trackFilecsvDecode = new String(request.getTrackFileCsv());
                /**
                 * write file to folder
                 */
                String urlfile = Base_url + System.currentTimeMillis() + ".csv";
                BufferedWriter writer = new BufferedWriter(new FileWriter(urlfile));
                writer.append(trackFilecsvDecode);
                writer.close();

//            System.out.println("csv " + trackFilecsvDecode);
                // set delimeter
                CSVFormat format = CSVFormat.newFormat(';').withHeader();
                //parse csv format String
//                log.info("1 ");
                CSVParser parser = new CSVParser(new StringReader(trackFilecsvDecode), format);

                List<OtnServiceGeometryPoints> geomPoints = new ArrayList<OtnServiceGeometryPoints>();
                //parse records (in the example 2 records)

                List timestamp = new ArrayList<String>();
                for (CSVRecord record : parser) {
                    timestamp.add(record.get("Timestamp"));
//                    System.out.println("latitude " + record.get("Latitude"));
//                    System.out.println("Longitude " + record.get("Longitude"));
//                    System.out.println("Timestamp " + record.get("Timestamp"));
//                    log.info("2");
                    geomPoints.add(new OtnServiceGeometryPoints(Double.parseDouble(record.get("Latitude")), Double.parseDouble(record.get("Longitude"))));
                }
//            System.out.println("number " + total_records);
//                System.out.println("first  time" + timestamp.get(0));
//                System.out.println("end  time" + timestamp.get(timestamp.size() - 1));
                String start_date = timestamp.get(0).toString();
                String end_date = timestamp.get(timestamp.size() - 1).toString();

                parser.close();
//                log.info("3");
//            if (request.getGeometryPoints() != null) {
//                geomPoints = request.getGeometryPoints();
//
//            }
                insertGeometryPoints(track.getTrackId(), geomPoints, startPointsList, endPointsList, start_date, end_date, urlfile);
            }
            response.setName(track.getName());
            response.setTrackId(track.getTrackId());
            response.setDescription(track.getDescription());
            response.setDistance(track.getDistance());
            response.setDuration(track.getDuration());
            if (transport_type != null) {
                response.setTransportName(transport_type.getName());
            }

            response.setSpeed(track.getSpeed());
            response.setElevation(track.getElevation());
            response.setLat_start(track.getLatStart());
            response.setLon_start(track.getLongStart());
            response.setLat_end(track.getLatEnd());
            response.setLon_end(track.getLongEnd());
            response.setDatetime_start(track.getDatetimeStart());
            response.setDatetime_end(track.getDatetimeEnd());
            response.setUserId(users.getUserId());
            response.setIs_public(track.getIsPublic());
            response.setRoute_kml(track.getRouteKlm());
            response.setTrackFileCsv(Base64.decodeBase64(track.getTrackFileCsv()));

            if (app != null) {
                response.setAppName(app.getName());
            }
            response.setStart_address(track.getStartAddress());
            response.setEnd_address(track.getEndAddress());
//            if (weather != null) {
//                response.setWeatherCondition(weather.getCondition());
//            }
            if (source != null) {
                response.setSourceName(source.getName());
            }
            return response;
//            }

        } catch (Exception e) {
            e.printStackTrace();

            response.setMessage("failure");
            response.setResponseCode(2);

        } finally {

            return response;
        }

    }//end addTracks()

    public OtnServiceTrackResponse updateTrack(OtnServiceTrackRequest request) {

        //*********************** Variables ***************************
        OtnServiceTrackResponse response = new OtnServiceTrackResponse();
        User users = null;

        TransportType transport_type = null;

        Weather weather;

        WeatherType weather_type;

        Apps app = null;

        Source source = null;

        Track track = new Track();

        TrackRating track_rating;

        TrackRatingType track_rating_type;

        List<Weather> weatherparamsList;

        ArrayList<OtnServiceWeatherRatings> weatherListparams = null;

        ArrayList<OtnServiceWeatherRatings> getWeather = null;

        //************************* Action ****************************
        System.out.println("------------Start---------------");

        try {

            //***************find the track record****************************
            track = em.find(Track.class, request.getTrackId());

            //**********************find user*****************************
            System.out.println("user " + request.getUserId());
            if (request.getUserId() != null || !request.getUserId().isEmpty()) {
                users = em.find(User.class, request.getUserId());

                if (users != null) {

                    System.out.println("=====================================");
                    System.out.println("user id " + users.getUserId());
                    track.setUserId(users);

                } else {
                    response.setMessage("user does not exist");
                    response.setResponseCode(1);
                    return response;
                }
            }

            //***********************find transportID******************************
            if (request.getTransportId() != 0) {

                transport_type = em.find(TransportType.class, request.getTransportId());

                if (transport_type != null) {
                    System.out.println("=====================================");
                    System.out.println("transport id " + transport_type.getTransportTypeId());
                    track.setTransportTypeId(transport_type);
                } else {
                    response.setMessage("transport type id does not exist");
                    response.setResponseCode(1);
                    return response;
                }
            } else {
                transport_type = track.getTransportTypeId();
            }
            //***********************find weatherID******************************
//            if (request.getWeatherId() != 0) {
//                weather = em.find(Weather.class, request.getWeatherId());
//
//                if (weather != null) {
//                    System.out.println("=====================================");
//                    System.out.println("weather id " + weather.getWeatherId());
////                    track.setWeatherId(weather);
//                } else {
//                    response.setMessage("weather condition id does not exist");
//                    response.setResponseCode(1);
//                    return response;
//                }
//            } else {
////                weather = track.getWeatherId();
//            }
            //***********************find app name******************************
            if (request.getAppId() != 0) {
                app = em.find(Apps.class, request.getAppId());

                if (app != null) {
                    System.out.println("=====================================");
                    System.out.println("app id " + app.getName());
                    track.setAppId(app);
                } else {
                    response.setMessage("application does not exist");
                    response.setResponseCode(1);
                    return response;
                }
            } else {
                app = track.getAppId();
            }
            //***********************find sourceId******************************
            if (request.getSourceId() != 0) {
                source = em.find(Source.class, request.getSourceId());

                if (source != null) {
                    System.out.println("=====================================");
                    System.out.println("source id " + source.getName());
                    track.setRouteSourceId(source);
                } else {
                    response.setMessage("source type id does not exist");
                    response.setResponseCode(1);
                    return response;
                }
            } else {
                source = track.getRouteSourceId();
            }

            if (track == null) {
                response.setMessage("there is no track with this trackId");
                response.setResponseCode(1);
                return response;
            } else {

                //***************Create track record****************************
                em.getTransaction().begin();
                if (request.getName() != null) {
                    track.setName(request.getName());
                }
                if (request.getDescription() != null) {
                    track.setDescription(request.getDescription());
                }
                if (request.getPicture() != null) {
                    track.setPicture(request.getPicture());
                }
                if (request.getDistance() != 0) {
                    track.setDistance(request.getDistance());
                }
                if (request.getDuration() != 0) {
                    track.setDuration(request.getDuration());
                }
                if (request.getSpeed() != 0) {
                    track.setSpeed(request.getSpeed());
                }
                if (request.getElevation() != 0) {
                    track.setElevation(request.getElevation());
                }
                if (request.getTrackFileCsv() != null) {
                    track.setTrackFileCsv(request.getTrackFileCsv());
                }
//            track.setRouteKlm(request.getRoute_klm());
                if (request.getLat_start() != 0) {
                    track.setLatStart(request.getLat_start());
                }
                if (request.getLon_start() != 0) {
                    track.setLongStart(request.getLon_start());
                }
                if (request.getLat_end() != 0) {
                    track.setLatEnd(request.getLat_end());
                }
                if (request.getLon_end() != 0) {
                    track.setLongEnd(request.getLon_end());
                }
                if (request.getDatetime_start() != null) {
                    track.setDatetimeStart(request.getDatetime_start());
                }
                if (request.getDatetime_end() != null) {
                    track.setDatetimeEnd(request.getDatetime_end());
                }
//                track.setUserId(users);
                if (request.isIs_public()) {
                    track.setIsPublic(request.isIs_public());
                }
                if (request.getStart_address() != null) {
                    track.setStartAddress(request.getStart_address());
                }
                if (request.getEnd_address() != null) {
                    track.setEndAddress(request.getEnd_address());
                }

                if (request.getTrackRatings() != null && request.getTrackRatings().size() > 0) {

                    OtnServiceTrackResponse rate_response = checkTrackRateAndType(request.getTrackRatings());

                    if (rate_response.getResponseCode() == 0) {
                        em.merge(track);
                        em.flush();
                        em.getTransaction().commit();

                        OtnServiceTrackRatingsRequest trackRatingRequest = new OtnServiceTrackRatingsRequest();
                        trackRatingRequest.setAppId(request.getAppId());
                        trackRatingRequest.setTrackId(request.getTrackId());
                        trackRatingRequest.setUserId(request.getUserId());
                        trackRatingRequest.setTrackRatings(request.getTrackRatings());

                        addTrackRatings(trackRatingRequest);

                    } else {

                        response.setMessage(rate_response.getMessage());
                        response.setResponseCode(rate_response.getResponseCode());
                        return response;
                    }

                } else {
                    em.merge(track);
                    em.flush();
                    em.getTransaction().commit();

                    response.setMessage("success");
                    response.setResponseCode(0);

                }
            }

            //***********************find weatherID******************************
            if (request.getWeatherList() != null && request.getWeatherList().size() > 0) {

                TypedQuery query = (TypedQuery) em.createQuery("SELECT w FROM Weather w WHERE w.trackId = :trackId");

                query.setParameter("trackId", track);
                //we use list to avoid "not found" exception
                weatherparamsList = query.getResultList();

                if (!weatherparamsList.isEmpty()) {

                    for (Weather weatherToRemoveList : weatherparamsList) {
                        em.getTransaction().begin();
                        em.remove(weatherToRemoveList);
                        em.flush();
                        em.getTransaction().commit();
                    }
                }
                weatherListparams = new ArrayList<OtnServiceWeatherRatings>();
                for (OtnServiceWeatherRatings weatherList : request.getWeatherList()) {
                    em.getTransaction().begin();

                    weather = new Weather();
                    weather_type = em.find(WeatherType.class, weatherList.getWeatherTypeId());

                    if (weather_type == null) {
                        response.setMessage("weather id  does not exist");
                        response.setResponseCode(1);
                        return response;

                    }

                    weather.setTrackId(track);
                    weather.setWeatherTypeId(weather_type);

                    em.persist(weather);
                    em.flush();
                    em.getTransaction().commit();
                    em.clear();

                    weatherListparams.add(new OtnServiceWeatherRatings(weather_type.getWeatherId()));

                    response.setMessage("success");
                    response.setResponseCode(0);

                }
                response.setWeatherList(weatherListparams);

            } else {
                TypedQuery query = (TypedQuery) em.createQuery("SELECT w FROM Weather w WHERE w.trackId = :trackId");

                query.setParameter("trackId", track);
                //we use list to avoid "not found" exception
                weatherparamsList = query.getResultList();

                if (!weatherparamsList.isEmpty()) {
                    getWeather = new ArrayList<OtnServiceWeatherRatings>();

                    for (Weather loadWeatherList : weatherparamsList) {

                        getWeather.add(new OtnServiceWeatherRatings(loadWeatherList.getWeatherTypeId().getWeatherId()));

                    }
                    response.setWeatherList(getWeather);

                }

            }

            //***************Create the response****************************
            response.setName(track.getName());
            response.setTrackId(track.getTrackId());
            response.setDescription(track.getDescription());
            response.setDistance(track.getDistance());
            response.setDuration(track.getDuration());
            if (transport_type != null) {
                response.setTransportName(transport_type.getName());
            }

            response.setSpeed(track.getSpeed());
            response.setElevation(track.getElevation());
            response.setLat_start(track.getLatStart());
            response.setLon_start(track.getLongStart());
            response.setLat_end(track.getLatEnd());
            response.setLon_end(track.getLongEnd());
            response.setDatetime_start(track.getDatetimeStart());
            response.setDatetime_end(track.getDatetimeEnd());
            response.setUserId(users.getUserId());
            response.setIs_public(track.getIsPublic());

            if (app != null) {
                response.setAppName(app.getName());
            }
            response.setStart_address(track.getStartAddress());
            response.setEnd_address(track.getEndAddress());

            if (source != null) {
                response.setSourceName(source.getName());
            }

            return response;

        } catch (Exception e) {
            e.printStackTrace();

            response.setMessage("failure");
            response.setResponseCode(2);
        } finally {

            return response;
        }

    }//end updateTracks()

    public OtnServicesMyTracksResponse myTracks(OtnServicesMyTracksRequest request) {

        //*********************** Variables ***************************
        OtnServicesMyTracksResponse response = new OtnServicesMyTracksResponse();

        User users;

        Apps app;

        TransportType transport_type;

        TypedQuery query;

        List<Track> tracksparamsList;

        ArrayList<OtnServicesMyTracksListResponse> myTracksList = new ArrayList<OtnServicesMyTracksListResponse>();

        TypedQuery subtracks_query;

        List<Track> subtracksparamsList;

        ArrayList<OtnServiceSubTracksResponse> subTrackList = null;

        //************************* Action ****************************
        System.out.println("------------Start---------------");

        try {
            //**********************find user*****************************

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

            //***************find user's tracks****************************
            query = (TypedQuery) em.createQuery("SELECT t FROM Track t WHERE t.userId = :userId and t.appId = :appId");

            query.setParameter("userId", users);
            query.setParameter("appId", app);

            //we use list to avoid "not found" exception
            tracksparamsList = query.getResultList();

            //if we found no results, the users is not registered 
            //so return error message
            if (tracksparamsList.isEmpty()) {
                response.setMessage("no available tracks for this user");
                response.setResponseCode(1);
                return response;

            } else {

                for (Track myTrack : tracksparamsList) {
//                    System.out.println("********************");
//
//                    System.out.println("my track name " + myTrack.getName());
//                    System.out.println("my track duration " + myTrack.getDuration());
//                    System.out.println("my track description " + myTrack.getDescription());

                    transport_type = em.find(TransportType.class, myTrack.getTransportTypeId().getTransportTypeId());

                    if (transport_type == null) {

                        response.setMessage("transport type id does not exist");
                        response.setResponseCode(1);
                        return response;
                    }

//                    //*********************find subtracks********************************
//                    System.out.println("parentrTrackid " + myTrack.getTrackId());
//
//                    subtracks_query = (TypedQuery) em.createQuery("SELECT t FROM Track t WHERE t.parentTrackId = :parentTrackId");
//
//                    subtracks_query.setParameter("parentTrackId", myTrack);
//
//                    //we use list to avoid "not found" exception
//                    subtracksparamsList = subtracks_query.getResultList();
//
//                    //if we found no results, the users is not registered 
//                    //so return error message
//                    if (!subtracksparamsList.isEmpty()) {
//                        subTrackList = new ArrayList<OtnServiceSubTracksResponse>();
//
//                        for (Track sTrack : subtracksparamsList) {
//                            System.out.println("sub track name " + sTrack.getName());
//                            transport_type = em.find(TransportType.class, sTrack.getTransportTypeId().getTransportTypeId());
//                            if (transport_type == null) {
//                                response.setMessage("transport type id does not exist");
//                                response.setResponseCode(1);
//                                return response;
//
//                            }
//
//                            subTrackList.add(new OtnServiceSubTracksResponse(sTrack.getName(), sTrack.getStartAddress(), sTrack.getLatStart(),
//                                    sTrack.getLongStart(), transport_type.getName(), sTrack.getDescription(), sTrack.getDistance(), sTrack.getDuration()));
//
//                        }
//                    }
                    //***********************add everything in subTrackList to consume JSON******************************
                    myTracksList.add(new OtnServicesMyTracksListResponse(myTrack.getName(), myTrack.getTrackId(), transport_type.getName(),
                            myTrack.getPicture(), myTrack.getDistance(), myTrack.getDuration(), myTrack.getStartAddress(), myTrack.getEndAddress()));

                }

                response.setTrackList(myTracksList);

            }

            response.setMessage("success");
            response.setResponseCode(0);

        } catch (Exception e) {
            e.printStackTrace();

            response.setMessage("failure");
            response.setResponseCode(2);
        } finally {

            return response;
        }

    }//end myTracks()

    public OtnServiceLoadTrackResponse getTrack(OtnServiceLoadTrackRequest request) {

        //*********************** Variables ***************************
        OtnServiceLoadTrackResponse response = new OtnServiceLoadTrackResponse();

        User users = null;

        Apps app;

        TransportType transport_type;

        TypedQuery query;

        List<Track> tracksparamsList;

//     
        List<Weather> weatherparamsList;

        Track track;

        ArrayList<OtnServicePoiAvgRatings> trackRatingList = null;

        ArrayList<OtnServiceWeatherRatings> weather = null;

        //************************* Action ****************************
        System.out.println("------------Start---------------");

        try {
            //**********************find user*****************************
            if (request.getUserId() != null && !request.getUserId().isEmpty()) {
                users = em.find(User.class, request.getUserId());

            }
            app = em.find(Apps.class, request.getAppId());

            if (app == null) {

                response.setMessage("application does not exist");
                response.setResponseCode(1);
                return response;
            }

            //***************find user's tracks****************************
            query = (TypedQuery) em.createQuery("SELECT t FROM Track t WHERE t.trackId= :trackId and t.appId = :appId");

//            query.setParameter("userId", users);
            query.setParameter("trackId", request.getTrackId());
            query.setParameter("appId", app);

            //we use list to avoid "not found" exception
            tracksparamsList = query.getResultList();

            //if we found no results, the users has no track with this trackId
            //so return error message
            if (tracksparamsList.isEmpty()) {
                response.setMessage("no available track with this trackId");
                response.setResponseCode(1);
                return response;

            } else {

                track = tracksparamsList.get(0);

                query = (TypedQuery) em.createQuery("SELECT t.trackRatingTypeId, AVG(t.rate) AS avgrates FROM TrackRating t WHERE t.trackId= :trackId  GROUP BY t.trackRatingTypeId ");

                query.setParameter("trackId", track);
//                query.setParameter("userId", users);

                //we use list to avoid "not found" exception
//                trackratingsparamsList = query.getResultList();
                List trackratingsparamsList = query.getResultList();

                Iterator itr = trackratingsparamsList.iterator();
                if (!trackratingsparamsList.isEmpty()) {
                    trackRatingList = new ArrayList<OtnServicePoiAvgRatings>();
                    while (itr.hasNext()) {
                        Object[] obj = (Object[]) itr.next();
                        TrackRatingType ratingtype = (TrackRatingType) obj[0];
                        Double avgRate = Double.parseDouble(String.valueOf(obj[1]));
//                        System.out.println("rate type id  " + ratingtype.getTrackRatingTypeId() + "rating avg  " + avgRate);
                        trackRatingList.add(new OtnServicePoiAvgRatings(ratingtype.getTrackRatingTypeId(), avgRate));
//
                    }
                }

                query = (TypedQuery) em.createQuery("SELECT w FROM Weather w WHERE w.trackId = :trackId");

                query.setParameter("trackId", track);

                //we use list to avoid "not found" exception
                weatherparamsList = query.getResultList();

                if (!weatherparamsList.isEmpty()) {
                    weather = new ArrayList<OtnServiceWeatherRatings>();

                    for (Weather weatherList : weatherparamsList) {

                        weather.add(new OtnServiceWeatherRatings(weatherList.getWeatherTypeId().getWeatherId()));

                    }

                }

                String geomPoints = getGeometryPoints(track.getTrackId());

                if (geomPoints != null) {
                    response.setGeomPoints(geomPoints);
                } else {
                    response.setGeomPoints("");
                }

                if (track.getIsPublic()) {
                    //provide a public track with no other check
                    response.setMessage("succsfull loading");
                    response.setResponseCode(0);
                    response.setName(track.getName());
                    response.setTrackId(track.getTrackId());
                    response.setUserId(track.getUserId().getUserId());
                    response.setDescription(track.getDescription());
                    response.setDistance(track.getDistance());
                    response.setDuration(track.getDuration());
                    response.setPicture(track.getPicture());
                    response.setTransportName(track.getTransportTypeId().getName());

                    response.setStart_address(track.getStartAddress());
                    response.setEnd_address(track.getEndAddress());

                    response.setRoute_kml(track.getRouteKlm());
                    response.setElevation(track.getElevation());
                    response.setSpeed(track.getSpeed());
                    response.setTrackFilecsv(Base64.decodeBase64(track.getTrackFileCsv()));
                    response.setIs_public(track.getIsPublic());
                    response.setTrackRatings(trackRatingList);
                    response.setWeatherList(weather);
                } else {
                    if (users != null) {
                        if (track.getUserId().getUserId().equalsIgnoreCase(request.getUserId())) {
                            //provide a private track that belongs to the owner
                            response.setMessage("succsfull loading");
                            response.setResponseCode(0);
                            response.setName(track.getName());
                            response.setTrackId(track.getTrackId());
                            response.setUserId(track.getUserId().getUserId());
                            response.setDescription(track.getDescription());
                            response.setDistance(track.getDistance());
                            response.setDuration(track.getDuration());
                            response.setPicture(track.getPicture());
                            response.setTransportName(track.getTransportTypeId().getName());

                            response.setStart_address(track.getStartAddress());
                            response.setEnd_address(track.getEndAddress());

                            response.setRoute_kml(track.getRouteKlm());
                            response.setElevation(track.getElevation());
                            response.setSpeed(track.getSpeed());
                            response.setTrackFilecsv(Base64.decodeBase64(track.getTrackFileCsv()));
                            response.setIs_public(track.getIsPublic());
                            response.setTrackRatings(trackRatingList);
                            response.setWeatherList(weather);

                        } else {
                            response.setMessage("the private track does not belong to this user");
                            response.setResponseCode(1);
                            return response;

                        }

                    } else {
                        response.setMessage("you shold provide a userId for a private track");
                        response.setResponseCode(1);
                        return response;
                    }

                }

            }

            response.setMessage("success");
            response.setResponseCode(0);

        } catch (Exception e) {
            e.printStackTrace();

            response.setMessage("failure");
            response.setResponseCode(2);
        } finally {

            return response;
        }

    }//end getTrack()

    public OtnServiceGetTracksResponse getTracks(OtnServiceGetTracksRequest request) {

        //*********************** Variables ***************************
        OtnServiceGetTracksResponse response = new OtnServiceGetTracksResponse();

        User users;

        Apps app;

        TypedQuery query;

        List<Track> tracksparamsList;

        ArrayList<OtnServicesMyTracksListResponse> myTracksList = new ArrayList<OtnServicesMyTracksListResponse>();

        ArrayList<OtnServiceGetTracksListResponse> trackList = null;

        int maxResults = 100;

        //************************* Action ****************************
        System.out.println("------------Start---------------");

//        if (em.getTransaction().isActive()) {
//            System.out.println("****************is active**************");
//            em.getTransaction().rollback();
//        }
        try {
            //**********************find user*****************************

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

            if ((request.getFromLat() != 0 && request.getFromLon() != 0) || (request.getToLat() != 0 && request.getToLon() != 0)) {
                List<OtnServiceGeometryPoints> startPointsList = new ArrayList<OtnServiceGeometryPoints>();
                OtnServiceGeometryPoints startPoints = new OtnServiceGeometryPoints();
                startPoints.setLatitude(request.getFromLat());
                startPoints.setLongitude(request.getFromLon());
                startPointsList.add(startPoints);

                List<OtnServiceGeometryPoints> toPointsList = new ArrayList<OtnServiceGeometryPoints>();
                OtnServiceGeometryPoints toPoints = new OtnServiceGeometryPoints();
                toPoints.setLatitude(request.getToLat());
                toPoints.setLongitude(request.getToLon());
                toPointsList.add(toPoints);

                double radius;
                if (request.getRadius() == 0) {

                    radius = 500;

                } else {
                    radius = request.getRadius();
                }

                trackList = getSpatialResults(startPointsList, toPointsList, radius, request,
                        app, users);

                response.setTrackList(trackList);

            } /* isPublic=null, isMine=null - just public tracks (without my tracks)*/ else if (request.isIsPublic() == null && request.isIsMine() == null) {

                //***************find public tracks****************************
                System.out.println("find public tracks");
                query = (TypedQuery) em.createQuery("SELECT t FROM Track t WHERE t.isPublic = true and t.appId = :appId order by t.creationDate desc ");
                query.setParameter("appId", app);
                query.setMaxResults(maxResults); //limit results
                //we use list to avoid "not found" exception
                tracksparamsList = query.getResultList();

                //if we found no results, no public tracks available
                //so return error message
                if (tracksparamsList.isEmpty()) {
                    response.setMessage("no public tracks for this app");
                    response.setResponseCode(1);
                    return response;

                } else {
                    response.setMessage("succsfull loading");
                    response.setResponseCode(0);
                    trackList = new ArrayList<OtnServiceGetTracksListResponse>();
                    for (Track track : tracksparamsList) {

                        String geomPoints = getGeometryPoints(track.getTrackId());

                        if (geomPoints == null) {
                            geomPoints = "";
                        }

                        trackList.add(new OtnServiceGetTracksListResponse(track.getName(), track.getTrackId(), track.getUserId().getUserId(),
                                track.getDescription(), track.getPicture(), track.getDistance(), track.getDuration(), track.getSpeed(),
                                track.getElevation(), track.getTransportTypeId().getName(),
                                track.getStartAddress(), track.getEndAddress(), track.getIsPublic(), geomPoints));

                    }
                    response.setTrackList(trackList);

                }
            } /* isPublic!=null and isPublic=true, isMine=null - just public tracks (without my tracks)*/ else if (!(request.isIsPublic() == null) && request.isIsPublic() && request.isIsMine() == null) {

                //***************find public tracks****************************
                System.out.println("find public tracks");
                query = (TypedQuery) em.createQuery("SELECT t FROM Track t WHERE t.isPublic = true and t.appId = :appId order by t.creationDate desc");
                query.setParameter("appId", app);
                //we use list to avoid "not found" exception
                tracksparamsList = query.getResultList();
                query.setMaxResults(maxResults); //limit results

                //if we found no results, no public tracks available
                //so return error message
                if (tracksparamsList.isEmpty()) {
                    response.setMessage("no public tracks for this app");
                    response.setResponseCode(1);
                    return response;

                } else {
                    response.setMessage("succsfull loading");
                    response.setResponseCode(0);
                    trackList = new ArrayList<OtnServiceGetTracksListResponse>();
                    for (Track track : tracksparamsList) {

                        String geomPoints = getGeometryPoints(track.getTrackId());

                        if (geomPoints == null) {
                            geomPoints = "";
                        }

                        trackList.add(new OtnServiceGetTracksListResponse(track.getName(), track.getTrackId(), track.getUserId().getUserId(),
                                track.getDescription(), track.getPicture(), track.getDistance(), track.getDuration(), track.getSpeed(),
                                track.getElevation(), track.getTransportTypeId().getName(),
                                track.getStartAddress(), track.getEndAddress(), track.getIsPublic(), geomPoints));

                    }
                    response.setTrackList(trackList);

                }
            } /* just isMine=true - all my tracks (public & private) */ else if (request.isIsPublic() == null && !(request.isIsMine() == null) && request.isIsMine()) {

                //***************find user's tracks****************************
                System.out.println("find user's tracks");
                query = (TypedQuery) em.createQuery("SELECT t FROM Track t WHERE t.userId = :userId and t.appId = :appId order by t.creationDate desc");

                query.setParameter("userId", users);
                query.setParameter("appId", app);

                //we use list to avoid "not found" exception
                tracksparamsList = query.getResultList();

                //if we found no results, the users is not registered 
                //so return error message
                if (tracksparamsList.isEmpty()) {
                    response.setMessage("no available tracks for this user");
                    response.setResponseCode(1);
                    return response;

                } else {

                    trackList = new ArrayList<OtnServiceGetTracksListResponse>();
                    for (Track track : tracksparamsList) {
//                    System.out.println("********************");
//
//                    System.out.println("my track name " + track.getName());
//                    System.out.println("my track duration " + track.getDuration());
//                    System.out.println("my track description " + track.getDescription());

                        String geomPoints = getGeometryPoints(track.getTrackId());

                        if (geomPoints == null) {
                            geomPoints = "";
                        }
                        //***********************add everything in subTrackList to consume JSON******************************
                        trackList.add(new OtnServiceGetTracksListResponse(track.getName(), track.getTrackId(), track.getUserId().getUserId(),
                                track.getDescription(), track.getPicture(), track.getDistance(), track.getDuration(), track.getSpeed(),
                                track.getElevation(), track.getTransportTypeId().getName(),
                                track.getStartAddress(), track.getEndAddress(), track.getIsPublic(), geomPoints));
                    }

                    response.setTrackList(trackList);
                }
            }//end if isMine
            /* isPublic=true, isMine=false - just public tracks (without my tracks)*/ else if (request.isIsPublic() && !request.isIsMine()) {

                //***************find public tracks****************************
                System.out.println("find public tracks");
                query = (TypedQuery) em.createQuery("SELECT t FROM Track t WHERE t.isPublic = true and t.userId <> :userId and t.appId = :appId order by t.creationDate desc");
                query.setParameter("appId", app);
                query.setParameter("userId", users);
                query.setMaxResults(maxResults); //limit results
                //we use list to avoid "not found" exception
                tracksparamsList = query.getResultList();

                //if we found no results, no public tracks available
                //so return error message
                if (tracksparamsList.isEmpty()) {
                    response.setMessage("no public tracks for this app");
                    response.setResponseCode(1);
                    return response;

                } else {
                    response.setMessage("succsfull loading");
                    response.setResponseCode(0);
                    trackList = new ArrayList<OtnServiceGetTracksListResponse>();
                    for (Track track : tracksparamsList) {

                        String geomPoints = getGeometryPoints(track.getTrackId());

                        if (geomPoints == null) {
                            geomPoints = "";
                        }

                        trackList.add(new OtnServiceGetTracksListResponse(track.getName(), track.getTrackId(), track.getUserId().getUserId(),
                                track.getDescription(), track.getPicture(), track.getDistance(), track.getDuration(), track.getSpeed(),
                                track.getElevation(), track.getTransportTypeId().getName(),
                                track.getStartAddress(), track.getEndAddress(), track.getIsPublic(), geomPoints));

                    }
                    response.setTrackList(trackList);

                }
            }//end if isPublic and not mine
            /* isPublic=true, isMine=true - all public tracks*/ else if ((request.isIsPublic() && request.isIsMine()) || request.isIsPublic()) {

                //***************find public tracks****************************
                System.out.println("find public tracks");
                query = (TypedQuery) em.createQuery("SELECT t FROM Track t WHERE t.isPublic = true and t.appId = :appId order by t.creationDate desc");
                query.setParameter("appId", app);
                query.setMaxResults(maxResults); //limit results
                //we use list to avoid "not found" exception
                tracksparamsList = query.getResultList();

                //if we found no results, no public tracks available
                //so return error message
                if (tracksparamsList.isEmpty()) {
                    response.setMessage("no public tracks for this app");
                    response.setResponseCode(1);
                    return response;

                } else {
                    response.setMessage("succsfull loading");
                    response.setResponseCode(0);
                    trackList = new ArrayList<OtnServiceGetTracksListResponse>();
                    for (Track track : tracksparamsList) {

                        String geomPoints = getGeometryPoints(track.getTrackId());

                        if (geomPoints == null) {
                            geomPoints = "";
                        }

                        trackList.add(new OtnServiceGetTracksListResponse(track.getName(), track.getTrackId(), track.getUserId().getUserId(),
                                track.getDescription(), track.getPicture(), track.getDistance(), track.getDuration(), track.getSpeed(),
                                track.getElevation(), track.getTransportTypeId().getName(),
                                track.getStartAddress(), track.getEndAddress(), track.getIsPublic(), geomPoints));

                    }
                    response.setTrackList(trackList);

                }
            }//end if isPublic
            /* isPublic=false and isMine=true - all my tracks (only private) */ else if (request.isIsMine() && !request.isIsPublic()) {
                //***************find user's tracks****************************
                System.out.println("find user's tracks");
                query = (TypedQuery) em.createQuery("SELECT t FROM Track t WHERE t.userId = :userId  and t.isPublic = false and t.appId = :appId order by t.creationDate desc");

                query.setParameter("userId", users);
                query.setParameter("appId", app);

                //we use list to avoid "not found" exception
                tracksparamsList = query.getResultList();

                //if we found no results, the users is not registered 
                //so return error message
                if (tracksparamsList.isEmpty()) {
                    response.setMessage("no available tracks for this user");
                    response.setResponseCode(1);
                    return response;

                } else {

                    trackList = new ArrayList<OtnServiceGetTracksListResponse>();
                    for (Track track : tracksparamsList) {
//                    System.out.println("********************");
//
//                    System.out.println("my track name " + track.getName());
//                    System.out.println("my track duration " + track.getDuration());
//                    System.out.println("my track description " + track.getDescription());

                        String geomPoints = getGeometryPoints(track.getTrackId());

                        if (geomPoints == null) {
                            geomPoints = "";
                        }
                        //***********************add everything in subTrackList to consume JSON******************************
                        trackList.add(new OtnServiceGetTracksListResponse(track.getName(), track.getTrackId(), track.getUserId().getUserId(),
                                track.getDescription(), track.getPicture(), track.getDistance(), track.getDuration(), track.getSpeed(),
                                track.getElevation(), track.getTransportTypeId().getName(),
                                track.getStartAddress(), track.getEndAddress(), track.getIsPublic(), geomPoints));
                    }

                    response.setTrackList(trackList);
                }
            }//end if isMine

            response.setMessage("success");
            response.setResponseCode(0);

        } catch (Exception e) {
            e.printStackTrace();

            response.setMessage("failure");
            response.setResponseCode(2);
        } finally {

            return response;
        }

    }//end getTracks()

    public OtnServiceLoadPublicTracksResponse loadPublicTracks() {

        //*********************** Variables ***************************
        OtnServiceLoadPublicTracksResponse response = new OtnServiceLoadPublicTracksResponse();

        TypedQuery query;

        List<Track> tracksparamsList;

        ArrayList<OtnServicesLoadPublicTracksListResponse> trackList = null;

        //************************* Action ****************************
        System.out.println("------------load public tracks---------------");

        try {

            //***************find user's tracks****************************
            query = (TypedQuery) em.createQuery("SELECT t FROM Track t WHERE t.isPublic = true");

            //we use list to avoid "not found" exception
            tracksparamsList = query.getResultList();

            //if we found no results, the users has no track with this trackId
            //so return error message
            if (tracksparamsList.isEmpty()) {
                response.setMessage("no public tracks");
                response.setResponseCode(1);
                return response;

            } else {
                response.setMessage("succsfull loading");
                response.setResponseCode(0);
                trackList = new ArrayList<OtnServicesLoadPublicTracksListResponse>();
                for (Track track : tracksparamsList) {

                    trackList.add(new OtnServicesLoadPublicTracksListResponse(track.getName(), track.getTrackId(), track.getDescription(),
                            track.getPicture(), track.getDistance(), track.getDuration(), track.getSpeed(), track.getElevation(),
                            track.getTransportTypeId().getName(), track.getRouteKlm(), track.getTrackFileCsv()));

                }
                response.setTrackList(trackList);

            }

        } catch (Exception e) {

            response.setMessage("failure");
            response.setResponseCode(2);
        } finally {

            return response;
        }

    }//end loadPublicTracks()

    public OtnServiceResponse deleteTrack(OtnServiceDeleteTrackRequest request) {
        OtnServiceResponse response = new OtnServiceResponse();

        Track track;

        try {
            em.getTransaction().begin();
            track = em.find(Track.class, request.getTrackId());

            em.remove(track);
            em.flush();
            em.getTransaction().commit();

            response.setMessage("success");
            response.setResponseCode(0);
        } catch (Exception e) {

            response.setMessage("failure");
            response.setResponseCode(2);
            return response;
        }
        return response;

    }//end deleteTrack()

    public OtnServiceStatisticsResponse addStatistics(int trackId, String type) {
        OtnServiceStatisticsResponse response = new OtnServiceStatisticsResponse();

        List<WebAnalytics> webAnalyticsList;

        TypedQuery query;

        WebAnalytics webAnalytics;

        Track track;

        try {
            //find track
            em.getTransaction().begin();
            track = em.find(Track.class, trackId);

            if (track == null) {
                response.setResponseCode(1);
                response.setMessage("track id does not exist");
                return response;

            }

            query = (TypedQuery) em.createQuery("SELECT w FROM WebAnalytics w WHERE w.trackId = :trackId");

            query.setParameter("trackId", track);

            //we use list to avoid "not found" exception
            webAnalyticsList = query.getResultList();

            if (webAnalyticsList.isEmpty()) {
                webAnalytics = new WebAnalytics();
                if (type.equals("views")) {
                    webAnalytics.setViewedCount(1);
                    webAnalytics.setNavigatedCount(0);
                } else {
                    webAnalytics.setViewedCount(0);
                    webAnalytics.setNavigatedCount(1);
                }
                webAnalytics.setTrackId(track);
                em.persist(webAnalytics);
                em.flush();
                em.getTransaction().commit();
                em.clear();

                response.setTrackId(trackId);
                response.setViews(webAnalytics.getViewedCount());
                response.setNavigation(webAnalytics.getNavigatedCount());

            } else {
                webAnalytics = webAnalyticsList.get(0);
                if (type.equals("views")) {
                    webAnalytics.setViewedCount(webAnalytics.getViewedCount() + 1);
                } else {
                    webAnalytics.setNavigatedCount(webAnalytics.getNavigatedCount() + 1);
                }

                em.merge(webAnalytics);
                em.flush();
                em.getTransaction().commit();
                response.setTrackId(trackId);
                response.setViews(webAnalytics.getViewedCount());
                response.setNavigation(webAnalytics.getNavigatedCount());

            }

            response.setMessage("success");
            response.setResponseCode(0);

            return response;

        } catch (Exception e) {

            response.setMessage("failure");
            response.setResponseCode(2);
            return response;
        }

    }//end addStatistics()

    public OtnServiceStatisticsResponse getStatistics(int trackId) {
        OtnServiceStatisticsResponse response = new OtnServiceStatisticsResponse();

        List<WebAnalytics> webAnalyticsList;

        TypedQuery query;

        WebAnalytics webAnalytics;

        Track track;
        try {
            //find track
            em.getTransaction().begin();
            track = em.find(Track.class, trackId);

            if (track == null) {
                response.setResponseCode(1);
                response.setMessage("track id does not exist");
                return response;

            }

            query = (TypedQuery) em.createQuery("SELECT w FROM WebAnalytics w WHERE w.trackId = :trackId");

            query.setParameter("trackId", track);

            //we use list to avoid "not found" exception
            webAnalyticsList = query.getResultList();

            if (webAnalyticsList.isEmpty()) {
                response.setMessage("no statistics for this user");
                response.setResponseCode(1);
                return response;

            } else {
                webAnalytics = webAnalyticsList.get(0);
                response.setTrackId(trackId);
                response.setViews(webAnalytics.getViewedCount());
                response.setNavigation(webAnalytics.getNavigatedCount());

                response.setMessage("success");
                response.setResponseCode(0);

                return response;

            }
        } catch (Exception e) {

            response.setMessage("failure");
            response.setResponseCode(2);
            return response;
        }

    }//end getStatistics()

    public OtnServiceResponse addTrackRatings(OtnServiceTrackRatingsRequest request) {
        OtnServiceResponse response = new OtnServiceResponse();

        List<TrackRating> trackRatingList;

        TypedQuery query;

        TrackRating trackRating;

        Track track;

        User user;

        Apps app;

        List<Track> trackratingsparamsList;
        TrackRatingType trackRatingType;
        try {
            //find track
            em.getTransaction().begin();
            track = em.find(Track.class, request.getTrackId());

            if (track == null) {
                response.setResponseCode(1);
                response.setMessage("track id does not exist");
                return response;

            }

            user = em.find(User.class, request.getUserId());

            if (user == null) {
                response.setResponseCode(1);
                response.setMessage("user does not exist");
                return response;

            }
            if (request.getAppId() != 0) {
                app = em.find(Apps.class, request.getAppId());

                if (app == null) {

                    response.setMessage("application does not exist");
                    response.setResponseCode(1);
                    return response;
                }

                query = (TypedQuery) em.createQuery("SELECT t FROM Track t WHERE t.trackId= :trackId and t.appId = :appId");
                System.out.println("track " + track);
                query.setParameter("trackId", track.getTrackId());
                query.setParameter("appId", app);

                //we use list to avoid "not found" exception
                trackratingsparamsList = query.getResultList();

                if (trackratingsparamsList.isEmpty()) {
                    response.setResponseCode(1);
                    response.setMessage("track id does not exist for this app");
                    return response;

                }
            }
            OtnServiceTrackResponse rate_response = checkTrackRateAndType(request.getTrackRatings());

            if (rate_response.getResponseCode() == 0) {

                for (OtnServicePoiRatings ratingList : request.getTrackRatings()) {
                    if (!em.getTransaction().isActive()) {
                        em.getTransaction().begin();
                    }

                    trackRatingType = em.find(TrackRatingType.class, ratingList.getRatingTypeId());

                    if (trackRatingType == null) {
                        response.setResponseCode(1);
                        response.setMessage("track rating type does not exist");
                        return response;

                    }
                    query = (TypedQuery) em.createQuery("SELECT t FROM TrackRating t WHERE t.userId = :userId and "
                            + " t.trackId = :trackId and t.trackRatingTypeId = :trackRatingTypeId");

                    query.setParameter("trackId", track);
                    query.setParameter("userId", user);
                    query.setParameter("trackRatingTypeId", trackRatingType);

                    //we use list to avoid "not found" exception
                    trackRatingList = query.getResultList();

                    if (trackRatingList.isEmpty()) {
                        trackRating = new TrackRating();

                        trackRating.setTrackId(track);
                        trackRating.setRate(ratingList.getRate());
                        trackRating.setTrackRatingTypeId(trackRatingType);
                        trackRating.setUserId(user);
                        em.persist(trackRating);
                        em.flush();
                        em.getTransaction().commit();
                        em.clear();

                    } else {
                        trackRating = trackRatingList.get(0);

                        trackRating.setRate(ratingList.getRate());

                        em.merge(trackRating);
                        em.flush();
                        em.getTransaction().commit();
                        em.clear();
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
            e.printStackTrace();

            response.setMessage("failure");
            response.setResponseCode(2);
            return response;
        }

    }//end addRatings()

    public OtnServiceTrackVerificationResponse addVerification(OtnServiceTrackVerificationRequest request) {
        OtnServiceTrackVerificationResponse response = new OtnServiceTrackVerificationResponse();

        Track track;

        TypedQuery query;

        User users;

        Apps app = null;

        TrackVerified trackVerified;

        try {

            users = em.find(User.class, request.getUserId());

            if (users == null) {
                response.setMessage("user does not exist");
                response.setResponseCode(1);
                return response;
            }

            //find track
            em.getTransaction().begin();

            //add verification to  track
            //***********************find app name******************************
            if (request.getAppId() != 0) {
                app = em.find(Apps.class, request.getAppId());
                if (app == null) {
                    response.setMessage("application does not exist");
                    response.setResponseCode(1);
                    return response;
                }
            }

            query = (TypedQuery) em.createQuery("SELECT t FROM Track t WHERE t.appId = :appId and t.trackId = :trackId ");

            query.setParameter("appId", app);
            query.setParameter("trackId", request.getTrackId());

            if (query.getResultList().isEmpty()) {
                response.setMessage("track id does not exist for this app");
                response.setResponseCode(1);
                return response;
            } else {
                track = (Track) query.getSingleResult();

            }

            query = (TypedQuery) em.createQuery("SELECT t FROM Track t WHERE t.appId = :appId and t.trackId = :trackId and t.userId != :userId");

            query.setParameter("appId", app);
            query.setParameter("trackId", request.getTrackId());
            query.setParameter("userId", users);

            if (query.getResultList().isEmpty()) {
                response.setResponseCode(501);
                response.setMessage("the owner of the track cannot verify the track");
                if (track.getVerified() != null) {
                    response.setVerification(track.getVerified());
                }
                return response;
            } else {
                track = (Track) query.getSingleResult();
            }

            query = (TypedQuery) em.createQuery("SELECT t FROM TrackVerified t WHERE t.userId = :userId and t.trackId = :trackId");

            query.setParameter("userId", users);
            query.setParameter("trackId", track);

            if (query.getResultList().isEmpty()) {
                if (track.getVerified() == null) {
                    track.setVerified(0);
                }
                track.setVerified(track.getVerified() + 1);
                em.merge(track);

                /**
                 * create new record to poi verified so this user cannot verify
                 * again the POI
                 */
                response.setVerification(track.getVerified());
                response.setTrackId(track.getTrackId());

                trackVerified = new TrackVerified();
                trackVerified.setTrackId(track);
                trackVerified.setUserId(users);

                em.persist(trackVerified);
                em.flush();
                em.getTransaction().commit();

                response.setMessage("success");
                response.setResponseCode(0);
            } else {
                response.setMessage("Route is already verified");
                response.setResponseCode(501);
                response.setVerification(track.getVerified());
                response.setTrackId(track.getTrackId());

            }

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("failure");
            response.setResponseCode(2);
            return response;
        } finally {
            em.clear();
        }

    }//end addVerification()

    public OtnServiceTrackVerificationResponse getVerification(int trackId) {

        OtnServiceTrackVerificationResponse response = new OtnServiceTrackVerificationResponse();

        Track track;

        try {

            //find track
            em.getTransaction().begin();

            track = em.find(Track.class, trackId);

            if (track == null) {
                response.setResponseCode(1);
                response.setMessage("track id does not exist");
                return response;

            }

            if (track.getVerified() == null) {
                response.setVerification(0);
            } else {
                response.setVerification(track.getVerified());
            }
            response.setTrackId(track.getTrackId());

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

    }//end getVerification()

    private OtnServiceTrackResponse checkSubTracksValidity(List<OtnServiceSubTracksRequest> subTracks) {
        OtnServiceTrackResponse response = new OtnServiceTrackResponse();
        try {
            for (OtnServiceSubTracksRequest sTrack : subTracks) {

                if (sTrack.getName() == null || sTrack.getLatitude() == 0 || sTrack.getLongitude() == 0
                        || sTrack.getTransportId() == 0 || sTrack.getDistance() == 0
                        || sTrack.getDuration() == 0 || sTrack.getName().isEmpty()) {
                    response.setMessage("null or empty params from subTracksList");
                    response.setResponseCode(1);
                    return response;

                } else {
                    response.setResponseCode(0);

                }

                //***********************find transportID******************************
                TransportType transport_type = em.find(TransportType.class, sTrack.getTransportId());
                if (transport_type == null) {
                    response.setMessage("transport type id does not exist");
                    response.setResponseCode(1);
                    return response;

                } else {
                    response.setResponseCode(0);
                }

            }
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    private OtnServiceTrackResponse checkTrackRateAndType(List<OtnServicePoiRatings> ratings) {
        OtnServiceTrackResponse response = new OtnServiceTrackResponse();
        try {
            for (OtnServicePoiRatings trackRatingList : ratings) {

                TrackRatingType track_rating_type = em.find(TrackRatingType.class, trackRatingList.getRatingTypeId());
                if (track_rating_type == null) {
                    response.setResponseCode(1);
                    response.setMessage("rating type does not exit");
                    return response;

                } else {
                    if (trackRatingList.getRate() >= 1 && trackRatingList.getRate() <= 5) {
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

    private void insertGeometryPoints(int trackId, List<OtnServiceGeometryPoints> geomPoints, List<OtnServiceGeometryPoints> startPoint,
            List<OtnServiceGeometryPoints> endPoint, String start_date, String end_date, String urlFile) {

        java.sql.Connection conpg;
        List<Point> points = new ArrayList<Point>();
        org.postgis.LineString linesToAdd = null;
        try {
//            log.info("4");
            System.out.println("********************");
            System.out.println("Insert Geometry points");

            /*
             * Load the JDBC driver and establish a connection.
             */
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/otn_web_app";
            conpg = DriverManager.getConnection(url, username, password);
//            conpg = DriverManager.getConnection(url, "postgres", "admin");

//            conpg = DriverManager.getConnection(url, "mobile_service", "otn_ck@n");

            /*
             * Add the geometry types to the connection. Note that you
             * must cast the connection to the pgsql-specific connection
             * implementation before calling the addDataType() method.
             */
            ((org.postgresql.PGConnection) conpg).addDataType("geometry", Class.forName("org.postgis.PGgeometry"));

            /*
             * Create a statement and execute a select query.
             */
            conpg.setAutoCommit(false);

            if (geomPoints != null) {
                for (OtnServiceGeometryPoints p : geomPoints) {
//                    System.out.println("long " + p.getLongitude());
//                    System.out.println("lat " + p.getLatitude());
//                    log.info("latidude " + p.getLatitude());
                    points.add(new Point(p.getLongitude(), p.getLatitude()));

//                points.add(new Point(30.123, 10.13));
//                points.add(new Point(10.123, 30.123));
//                points.add(new Point(40.13, 40.123));
                }
                linesToAdd = new org.postgis.LineString(points.toArray(new Point[]{}));
                linesToAdd.setSrid(4326);
            }
            org.postgis.Point startPointToAdd = new org.postgis.Point();

            for (OtnServiceGeometryPoints sp : startPoint) {
//                System.out.println("start long " + sp.getLongitude());
//                System.out.println("start lat " + sp.getLatitude());
//                log.info("start lat " + sp.getLatitude());
                startPointToAdd.setX(sp.getLongitude());
                startPointToAdd.setY(sp.getLatitude());
            }
            startPointToAdd.setSrid(4326);
            org.postgis.Point endPointToAdd = new org.postgis.Point();
            for (OtnServiceGeometryPoints ep : endPoint) {
//                System.out.println("end long " + ep.getLongitude());
//                System.out.println("end lat " + ep.getLatitude());
//                log.info("start lat " + ep.getLatitude());
                endPointToAdd.setX(ep.getLongitude());
                endPointToAdd.setY(ep.getLatitude());
            }
            endPointToAdd.setSrid(4326);
//            log.info("trackId : " + trackId);
//            log.info("start_date : " + start_date);
//            log.info("end_date : " + end_date);
            PreparedStatement psSE = conpg.prepareStatement("INSERT INTO otn_web_app.track_spatial (track_id,geom,start_point,end_point,start_time,end_time,url_file) VALUES (?, ?, ?, ?, ?, ?, ?)");

            psSE.setInt(1, trackId);
            psSE.setObject(2, new org.postgis.PGgeometry(linesToAdd));
            psSE.setObject(3, new org.postgis.PGgeometry(startPointToAdd));
            psSE.setObject(4, new org.postgis.PGgeometry(endPointToAdd));
            psSE.setObject(5, start_date);
            psSE.setObject(6, end_date);
            psSE.setObject(7, urlFile);
            System.out.println("psSE before: " + psSE.toString());
            log.info("psSE before: " + psSE.toString());
            psSE.execute();

//            log.info("psSE after: " + psSE.toString());
//            log.info("5");
            psSE.close();
            conpg.commit();
            conpg.close();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }//end insertGeometryPoints

    private String getGeometryPoints(int trackId) throws ClassNotFoundException {

        java.sql.Connection conpg = null;
        PreparedStatement stmt = null;
        try {
            /*
             * Load the JDBC driver and establish a connection.
             */
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/otn_web_app";
            conpg = DriverManager.getConnection(url, username, password);
//            conpg = DriverManager.getConnection(url, "postgres", "admin");
//            conpg = DriverManager.getConnection(url, "mobile_service", "otn_ck@n");

            //*********************Read data*****************************
            stmt = conpg.prepareStatement("SELECT id, geom FROM otn_web_app.track_spatial WHERE track_id = ?");
            stmt.setInt(1, trackId);
            ResultSet r = stmt.executeQuery();
            String geomPoints = null;

//            Statement stmt = conpg.createStatement();
//            ResultSet r = stmt.executeQuery("SELECT id, geom FROM otn_web_app.track_spatial ");
            while (r.next()) {
                /*
                 * Retrieve the geometry as an object then cast it to the geometry type.
                 * Print things out.
                 */
                PGgeometry geom = (PGgeometry) r.getObject(2);
                int id = r.getInt(1);
//                System.out.println("Row " + id + ":");
//                System.out.println(geom.toString());
                geomPoints = geom.toString();
            }
            r.close();

            stmt.close();
            return geomPoints;
        } catch (SQLException ex) {
            //Logger.getLogger(InsertPointPostGis.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conpg.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conpg != null) {
                    conpg.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }

    }//end getGeometryPoints

    private ArrayList<OtnServiceGetTracksListResponse> getSpatialResults(List<OtnServiceGeometryPoints> fromPoints,
            List<OtnServiceGeometryPoints> toPoints, double radius, OtnServiceGetTracksRequest request, Apps appId, User userId) throws ClassNotFoundException {

        java.sql.Connection conpg = null;
        PreparedStatement stmt = null;

//        Track track;
        ArrayList<OtnServiceGetTracksListResponse> trackList;
        TypedQuery query;
        List<Track> tracksparamsList;
        StringBuilder sb;
        int maxResults = 100;
        try {
            /*
             * Load the JDBC driver and establish a connection.
             */
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/otn_web_app";
            conpg = DriverManager.getConnection(url, username, password);
//            conpg = DriverManager.getConnection(url, "postgres", "admin");
//            conpg = DriverManager.getConnection(url, "mobile_service", "otn_ck@n");

            //*********************Read data*****************************
//            Point fromPoint = new Point(fromPoints.get(0).getLongitude(), fromPoints.get(0).getLatitude());
//
//            Point toPoint = new Point(toPoints.get(0).getLongitude(), toPoints.get(0).getLatitude());
//            PreparedStatement stmt = conpg.prepareStatement("SELECT id,geom, start_point, end_point,track_id FROM otn_web_app.track_spatial WHERE ST_Distance_Sphere(start_point,ST_GeomFromText(?)) < ?  AND ST_Distance_Sphere(end_point, ST_GeomFromText(?)) < ?");
//              PreparedStatement stmt = conpg.prepareStatement("SELECT id, end_point,ST_DistanceSphere(end_point, ?) FROM otn_web_app.track_spatial WHERE id > 20  ");
//            stmt.setObject(1, new org.postgis.PGgeometry(fromPoint));
//            stmt.setDouble(2, radius);
//            stmt.setObject(3, new org.postgis.PGgeometry(toPoint));
//            stmt.setDouble(4, radius);
            if (fromPoints.get(0).getLatitude() != 0 && toPoints.get(0).getLatitude() != 0) {

                stmt = conpg.prepareStatement("SELECT id,geom, start_point, end_point,track_id FROM otn_web_app.track_spatial WHERE ST_Distance_Sphere(start_point,ST_GeomFromText('POINT("
                        + fromPoints.get(0).getLongitude() + " " + fromPoints.get(0).getLatitude() + ")',4326)) < "
                        + radius + " AND ST_Distance_Sphere(end_point, ST_GeomFromText('POINT(" + toPoints.get(0).getLongitude()
                        + " " + toPoints.get(0).getLatitude() + ")',4326)) <" + radius + " ");

            } else if (fromPoints.get(0).getLatitude() != 0 && toPoints.get(0).getLatitude() == 0) {

                stmt = conpg.prepareStatement("SELECT id,geom, start_point, end_point,track_id FROM otn_web_app.track_spatial WHERE ST_Distance_Sphere(start_point,ST_GeomFromText('POINT(" + fromPoints.get(0).getLongitude() + " " + fromPoints.get(0).getLatitude() + ")',4326)) < " + radius + " ");

            }
            if (fromPoints.get(0).getLatitude() == 0 && toPoints.get(0).getLatitude() != 0) {

                stmt = conpg.prepareStatement("SELECT id,geom, start_point, end_point,track_id FROM otn_web_app.track_spatial WHERE ST_Distance_Sphere(end_point, ST_GeomFromText('POINT(" + toPoints.get(0).getLongitude() + " " + toPoints.get(0).getLatitude() + ")',4326)) <" + radius + " ");

            }

            System.out.println(stmt.toString());

            String geomPoints;
            ResultSet r = stmt.executeQuery();
//            Statement stmt = conpg.createStatement();
//            ResultSet r = stmt.executeQuery("SELECT id, geom FROM otn_web_app.track_spatial ");
            trackList = new ArrayList<OtnServiceGetTracksListResponse>();
            while (r.next()) {
                sb = new StringBuilder();
                /*
                 * Retrieve the geometry as an object then cast it to the geometry type.
                 * Print things out.
                 */
                int id = r.getInt(1);
                int track_id = r.getInt(5);
                PGgeometry geom = (PGgeometry) r.getObject(2);
                PGgeometry start_point = (PGgeometry) r.getObject(3);
                PGgeometry end_point = (PGgeometry) r.getObject(4);

                System.out.println("Row " + id + ":");
                System.out.println(geom.toString());
                System.out.println(start_point);
                System.out.println(end_point);

                geomPoints = geom.toString();
//                track = em.find(Track.class, track_id);
                String queryString = "SELECT t FROM Track t WHERE t.trackId = :trackId and t.appId = :appId";

                sb.append(queryString);

                if (request.isIsPublic() == null && request.isIsMine() == null) {
                    sb.append(" and t.isPublic = true");

                } else if (!(request.isIsPublic() == null) && request.isIsPublic() && request.isIsMine() == null) {
                    sb.append(" and t.isPublic = true");

                } else if (request.isIsPublic() == null && !(request.isIsMine() == null) && request.isIsMine()) {
                    System.out.println("is mine");
                    sb.append(" and t.userId = :userId");

                } else if (request.isIsPublic() && !request.isIsMine()) {
                    sb.append(" and t.isPublic = true and  t.userId <> :userId");
                } else if (request.isIsPublic() && request.isIsMine()) {
                    sb.append(" and t.isPublic = true");
                } else if (request.isIsMine() && !request.isIsPublic()) {

                    sb.append(" and t.userId = :userId  and t.isPublic = false");

                }

                sb.append(" order by t.creationDate desc ");

                query = em.createQuery(sb.toString(), Track.class);

                query.setParameter("trackId", track_id);
                query.setParameter("appId", appId);

                if (request.isIsMine() != null) {
                    if (!(!(request.isIsPublic() == null) && request.isIsPublic() && request.isIsMine())) {
                        query.setParameter("userId", userId);
                    }
                }

                if (request.isIsPublic() == null && request.isIsMine() == null) {
                    query.setMaxResults(maxResults);

                } else if (!(request.isIsPublic() == null) && request.isIsPublic() && request.isIsMine() == null) {
                    query.setMaxResults(maxResults);
                } else if (request.isIsPublic() && !request.isIsMine()) {
                    query.setMaxResults(maxResults);
                } else if (request.isIsPublic() && request.isIsMine()) {
                    query.setMaxResults(maxResults);
                }

//                query = (TypedQuery) em.createQuery("SELECT t FROM Track t WHERE t.trackId = :trackId and t.userId <> :userId and t.appId = :appId order by t.creationDate desc");
//                query.setParameter("trackId", track_id);
                //we use list to avoid "not found" exception
                tracksparamsList = query.getResultList();

                //if we found no results, no public tracks available
                //so return error message
                if (!tracksparamsList.isEmpty()) {

                    for (Track track : tracksparamsList) {

                        trackList.add(new OtnServiceGetTracksListResponse(track.getName(), track.getTrackId(), track.getUserId().getUserId(),
                                track.getDescription(), track.getPicture(), track.getDistance(), track.getDuration(), track.getSpeed(),
                                track.getElevation(), track.getTransportTypeId().getName(),
                                track.getStartAddress(), track.getEndAddress(), track.getIsPublic(), geomPoints));

                    }
                }

            }
            r.close();
//            conpg.commit();
//            conpg.close();
            stmt.close();
            return trackList;
        } catch (SQLException ex) {
            //Logger.getLogger(InsertPointPostGis.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conpg.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conpg != null) {
                    conpg.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }

    }//end getSpatialResults
}//end class
