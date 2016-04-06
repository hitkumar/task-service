package com.tradeshift;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;

@Component
@Path("hello")
public class HelloResource {
    private HelloService helloService;

    @Autowired
    public HelloResource(HelloService helloService) {
        this.helloService = helloService;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayIt() {
        return helloService.sayIt();
    }

    @POST
    @Path("post")
    @Produces(MediaType.TEXT_PLAIN)
    public void saveOne() {
        helloService.saveOne("It works!");
    }

    @GET
    @Path("get")
    @Produces(MediaType.TEXT_PLAIN)
    public String getOne() {
        return helloService.getOne();
    }
}
