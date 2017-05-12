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
import otn.mobile.services.PostgressEntity;

/**
 *
 * @author EMantziou
 */
@Stateless
@Path("postgressentity")
public class PostgressEntityFacadeREST extends AbstractFacade<PostgressEntity> {

    @PersistenceContext(unitName = "OTNplatformPU")
    private EntityManager em;

    public void init() {
        EntityManagerFactory factory;
        factory = Persistence.createEntityManagerFactory("OTNplatformPU");
        em = factory.createEntityManager();
    }

    public PostgressEntityFacadeREST() {
        super(PostgressEntity.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(PostgressEntity entity) {
        if (em == null) {
            init();
        }
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, PostgressEntity entity) {
        if (em == null) {
            init();
        }
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        if (em == null) {
            init();
        }
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public PostgressEntity find(@PathParam("id") Long id) {
        if (em == null) {
            init();
        }
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<PostgressEntity> findAll() {
        if (em == null) {
            init();
        }
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<PostgressEntity> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        if (em == null) {
            init();
        }
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        if (em == null) {
            init();
        }
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {

        return em;
    }

}
