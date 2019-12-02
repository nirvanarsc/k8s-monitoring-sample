package com.linecorp.k8s.monitoring.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.internal.shaded.guava.collect.ImmutableList;
import com.linecorp.armeria.server.thrift.THttpService;
import com.linecorp.armeria.spring.AnnotatedServiceRegistrationBean;
import com.linecorp.armeria.spring.ThriftServiceRegistrationBean;
import com.linecorp.k8s.monitoring.sample.service.SampleService;
import com.linecorp.k8s.monitoring.sample.thrift.GreetingService;
import com.linecorp.k8s.monitoring.sample.thrift.GreetingService.hello_args;

@Configuration
public class ArmeriaConfiguration {
    @Bean
    public ThriftServiceRegistrationBean greetingService(GreetingService.Iface greetingService) {
        return new ThriftServiceRegistrationBean()
                .setPath("/thrift/greeting")
                .setService(THttpService.of(greetingService))
                .setServiceName("GreetingService")
                .setDecorators()
                .setExampleRequests(ImmutableList.of(new hello_args("peepo")));
    }

    @Bean
    public AnnotatedServiceRegistrationBean sampleServiceBean(SampleService sampleService) {
        return new AnnotatedServiceRegistrationBean()
                .setService(sampleService)
                .setServiceName("SampleService");
    }
}
