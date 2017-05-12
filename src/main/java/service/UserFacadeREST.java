/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import otn.mobile.persistency.User;

/**
 *
 * @author EMantziou
 */
@Stateless
@Path("user")
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "OTNplatformPU")
    private EntityManager em;

    public void init() {
        EntityManagerFactory factory;
        factory = Persistence.createEntityManagerFactory("OTNplatformPU");
        em = factory.createEntityManager();
    }

    public UserFacadeREST() {
        super(User.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(User entity) {
        getEntityManager().getTransaction().begin();
        super.create(entity);
        getEntityManager().flush();
        getEntityManager().getTransaction().commit();
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, User entity) {
      
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public User find(@PathParam("id") Integer id) {
       
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<User> findAll() {
       
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
       
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
       
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
         if (em == null) {
            init();
        }
        return em;
    }

}
