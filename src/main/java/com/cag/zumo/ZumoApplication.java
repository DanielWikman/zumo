package com.cag.zumo;

import com.cag.zumo.health.ZumoHealth;
import com.cag.zumo.resources.ZumoResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by dawi on 2016-10-06.
 */
public class ZumoApplication  extends Application<ZumoConfiguration> {

    public static void main(String[] args) throws Exception {
        new ZumoApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<ZumoConfiguration> bootstrap) {
        // Nothing to do yet;
    }

    @Override
    public void run(ZumoConfiguration zumoConfiguration, Environment environment) throws Exception {
        ZumoResource resource = new ZumoResource();
        environment.jersey().register(resource);
        environment.healthChecks().register("zumo", new ZumoHealth());
    }
}
