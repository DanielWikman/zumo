package com.cag.zumo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by dawi on 2016-10-06.
 */
public class ZumoConfiguration extends Configuration {
    @NotEmpty
    private String registry;

    @NotEmpty
    private String serviceName;

    @JsonProperty
    public String getRegistry() {
        return registry;
    }

    @JsonProperty
    public void setRegistry(String registry) {
        this.registry = registry;
    }

    @JsonProperty
    public String getServiceName() {
        return serviceName;
    }

    @JsonProperty
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
