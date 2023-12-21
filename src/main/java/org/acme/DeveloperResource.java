package org.acme;

import dto.DeveloperData;
import entity.Developer;
import io.quarkus.panache.common.Sort;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("developer")
public class DeveloperResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response developers() {
        //     List<Developer>developers = Developer.findAll().list();
        //     List<Developer>developers = Developer.listAll();
        //     List<Developer>developers = Developer.listAll(Sort.ascending("name"));
        List<Developer> developers = Developer.findAll(Sort.descending("name")).list();
        return Response.ok(developers).build();
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDeveloper(DeveloperData developerData) {
        Developer developer= new Developer();
        developer.setName(developerData.getName());
        developer.setSkill(developerData.getSkill());

        developer.persist();

   //     developer.persistAndFlush(); // this will update the data to database immediately but keep in mind that it may have performance implications and some reason you might want to control when the flush occurs the optimization reasons.

        return Response.ok().build();
    }
    @PUT
    @Path("{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDeveloper(@PathParam("id") Long id,DeveloperData developerData){
        Optional<Developer>developerOptional = Developer.findByIdOptional(id);
        if(developerOptional.isPresent()){
            Developer developer = developerOptional.get();
            developer.setName(developerData.getName());
            developer.setSkill(developerData.getSkill());
            developer.persist();

            //     developer.persistAndFlush(); // this will update the data to database immediately but keep in mind that it may have performance implications and some reason you might want to control when the flush occurs the optimization reasons.
            return  Response.ok().build();
        }
        return  Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDeveloper(@PathParam("id") Long id,DeveloperData developerData){
        Optional<Developer>developerOptional = Developer.findByIdOptional(id);
        if(developerOptional.isPresent()){
            developerOptional.get().delete();
            return  Response.ok().build();
        }
        return  Response.status(Response.Status.NOT_FOUND).build();

    }

}