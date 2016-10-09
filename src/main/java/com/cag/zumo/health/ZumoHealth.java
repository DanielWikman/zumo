package com.cag.zumo.health;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by dawi on 2016-10-06.
 */
public class ZumoHealth extends HealthCheck {

    public ZumoHealth() {
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
