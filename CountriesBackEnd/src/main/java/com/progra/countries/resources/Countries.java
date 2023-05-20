/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.countries.resources;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import com.progra.countries.logic.Country;
import com.progra.countries.logic.Service;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/countries")
@PermitAll
public class Countries {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Country> find(@DefaultValue("") @QueryParam("name") String name) {
        return Service.instance().find(name);
    }

    @GET
    @Path("{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Country read(@PathParam("name") String name) {
        try {
            return Service.instance().read(name);
        } catch (Exception ex) {
            throw new NotFoundException();
        }
    }

    @DELETE
    public void delete(@DefaultValue("") @QueryParam("name") String name) {
        try {
            Service.instance().delete(name);
        } catch (Exception ex) {
            throw new NotFoundException(); //404
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Response agregarPais(Country country) {
        Service.instance().add(country);
        return Response.status(Response.Status.CREATED).entity("País agregado correctamente").build();
    }

    @POST
    @Path("/login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public void addCountry(Country candidate) {
        try {
            Service.instance().add(candidate);
        } catch (Exception ex) {
            //Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException();
        }
    }
}
