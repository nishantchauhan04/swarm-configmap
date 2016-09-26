package org.configmap.demo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * @author kameshs
 */
@Path("/api")
@Produces(MediaType.TEXT_PLAIN)
@Api(value = "/api", description = "Displays the value environment property", tags = "env,value")
public class ConfigValuePrinter {

    @GET
    @Path("/env/{propName}")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Prints the value of an environment property", response = Response.class,
            notes = "environment property printer")
    public Response envValue(@PathParam("propName") String propName) {
        String propValue = System.getenv().getOrDefault(propName, "Unknown");
        return Response.status(200).entity(
                String.format("Config %s has a value %s ", propName, propValue)).build();
    }


    @GET
    @Path("/chant")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Prints the value of an environment property configured via ConfigMap", response = Response.class,
            notes = "environment property printer")
    public Response envValue() {
        String propOneFile = "/etc/config/prop.one";
        String propTwoFile = "/etc/config/prop.two";

        String configValues = null;
        try {
            configValues = Files.readAllLines(Paths.get(propOneFile, propTwoFile))
                    .stream()
                    .collect(Collectors.joining(" "));
            return Response.status(200).entity(
                    String.format("%s", configValues)).build();
        } catch (IOException e) {
            return Response.status(504).entity(
                    String.format("Value not found")).build();
        }

    }


}
