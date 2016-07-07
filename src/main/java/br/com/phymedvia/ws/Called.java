package br.com.phymedvia.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author Willian
 */
@Path("generic")
public class Called {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Called
     */
    public Called() {
    }

    /**
     * Retrieves representation of an instance of br.com.phymedvia.ws.Called
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        return "ok";
    }

    /**
     * PUT method for updating or creating an instance of Called
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
