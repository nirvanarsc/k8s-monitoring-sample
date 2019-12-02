package com.linecorp.k8s.monitoring.sample.service;

import javax.inject.Named;

import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;
import com.linecorp.armeria.server.annotation.PathPrefix;

@Named
@PathPrefix("/hello")
public class SampleService {
    @Get("/{name}")
    public String hello(@Param String name) {
        return "Hello armeria " + name;
    }
}
