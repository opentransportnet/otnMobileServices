/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package otn.mobile.services.application;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author sakis
 */
@javax.ws.rs.ApplicationPath("platform")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        // following code can be used to customize Jersey 1.x JSON provider:
        try {
            Class jacksonProvider = Class.forName("org.codehaus.jackson.jaxrs.JacksonJsonProvider");
            resources.add(jacksonProvider);
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(otn.mobile.services.PostgresCall.class);
        resources.add(otn.mobile.services.issueServices.class);
        resources.add(otn.mobile.services.poiServices.class);
        resources.add(otn.mobile.services.trackServices.class);
        resources.add(otn.mobile.services.userServices.class);
        resources.add(service.AppsFacadeREST.class);
        resources.add(service.IssueFacadeREST.class);
        resources.add(service.IssueTypeFacadeREST.class);
        resources.add(service.PoiFacadeREST.class);
        resources.add(service.PoiRatingFacadeREST.class);
        resources.add(service.PoiRatingTypeFacadeREST.class);
        resources.add(service.PostgressEntityFacadeREST.class);
        resources.add(service.PublicPoiCategoryFacadeREST.class);
        resources.add(service.PublicPoiFacadeREST.class);
        resources.add(service.SourceFacadeREST.class);
        resources.add(service.TrackFacadeREST.class);
        resources.add(service.TrackRatingFacadeREST.class);
        resources.add(service.TrackRatingTypeFacadeREST.class);
        resources.add(service.TrackfileCsvFacadeREST.class);
        resources.add(service.TransportTypeFacadeREST.class);
        resources.add(service.UserFacadeREST.class);
        resources.add(service.WeatherFacadeREST.class);
        resources.add(service.WeatherTypeFacadeREST.class);
        resources.add(service.WebAnalyticsFacadeREST.class);
     
    }
    
}
