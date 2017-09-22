package com.snapchat;

import com.snapchat.jobs.QuartzHandler;
import com.snapchat.jobs.ValidateTimeJob;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.net.httpserver.HttpServer;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by apolonxviii on 22.09.17.
 */
public class ServerInit {

    private static URI baseUri = UriBuilder.fromUri("http://localhost/").port(8082).build();
    private static ResourceConfig config = new PackagesResourceConfig("com.snapchat");
    private static HttpServer server = null;

    public static void main(String[] args) throws Exception {
        server = HttpServerFactory.create(baseUri, config);
        server.start();
        QuartzHandler.shedule(ValidateTimeJob.class);
    }

    public static void stopServer() {
        server.stop(0);
    }

}
