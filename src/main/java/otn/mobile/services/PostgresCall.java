/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.services;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import otn.mobile.persistency.User;

/**
 *
 * @author EMantziou
 */
@Stateless
@Path("postgres")
public class PostgresCall {

    @PersistenceContext(unitName = "OTNplatformPU")
    private EntityManager em;

    public void init() {
        EntityManagerFactory factory;
        factory = Persistence.createEntityManagerFactory("OTNplatformPU");
        em = factory.createEntityManager();
    }

    @POST
    @Path("/callUsers")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public String findUsers() {

        if (em == null) {
            init();
        }


        System.out.println("------------Start---------------");

        TypedQuery query;
        User users;

        List<User> usersparamsList;

        query = (TypedQuery) em.createQuery("SELECT u FROM User u");

        //we use list to avoid "not found" exception
        usersparamsList = query.getResultList();

        //if we found no results, the users is not registered 
        //so return error message
        if (usersparamsList.isEmpty()) {

            return "false";

        } else {
            //get the subscribed service record
            users = usersparamsList.get(0);

            System.out.println("=====================================");
            System.out.println("user id " + users.getUserId());
         
            return "true";
        }
    }

}//end class
