package com.cag.zumo;

import com.cag.zumo.model.DecayMode;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.core.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import jakarta.validation.constraints.NotNull;

/**
 * Created by dawi on 2016-10-06.
 */
public class ZumoConfiguration extends Configuration {
    @NotEmpty
    private String registry;

    @NotEmpty
    private String serviceName;

    @NotNull
    private DecayMode decayMode;

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

    public DecayMode getDecayMode() {
        return decayMode;
    }

    public void setDecayMode(DecayMode decayMode) {
        this.decayMode = decayMode;
    }
}
