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
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

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
    @Path("{name}")
    public void delete(@PathParam("name") String name) {
        Service.instance().delete(name);
    }     
}
