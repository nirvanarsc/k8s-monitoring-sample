package com.linecorp.k8s.monitoring.sample.service;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.linecorp.k8s.monitoring.sample.thrift.Greeting;
import com.linecorp.k8s.monitoring.sample.thrift.GreetingService;

@Component
public class GreetingServiceImpl implements GreetingService.Iface {
    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Override
    public Greeting hello(String name) throws TException {
        return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name));
    }
}
