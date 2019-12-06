package com.github.nirvanarsc.k8s.monitoring.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.nirvanarsc.k8s.monitoring.sample.grpc.GreetingServiceGrpc;
import com.github.nirvanarsc.k8s.monitoring.sample.grpc.Welcome.GreetingRequest;
import com.github.nirvanarsc.k8s.monitoring.sample.service.GreetingService;
import com.github.nirvanarsc.k8s.monitoring.sample.service.SampleService;

import com.linecorp.armeria.common.grpc.GrpcSerializationFormats;
import com.linecorp.armeria.server.grpc.GrpcService;
import com.linecorp.armeria.spring.AnnotatedServiceRegistrationBean;
import com.linecorp.armeria.spring.GrpcExampleRequest;
import com.linecorp.armeria.spring.GrpcServiceRegistrationBean;

@Configuration
public class ArmeriaConfiguration {
    @Bean
    public AnnotatedServiceRegistrationBean sampleServiceBean(SampleService sampleService) {
        return new AnnotatedServiceRegistrationBean()
                .setService(sampleService)
                .setServiceName("SampleService");
    }

    @Bean
    public GrpcServiceRegistrationBean grpcServiceBean() {
        return new GrpcServiceRegistrationBean()
                .setServiceName("GreetingService")
                .setService(GrpcService.builder()
                                       .addService(new GreetingService())
                                       .supportedSerializationFormats(GrpcSerializationFormats.values())
                                       .enableUnframedRequests(true)
                                       .build())
                .addExampleRequests(buildExampleRequest("Hello"),
                                    buildExampleRequest("LazyHello"));
    }

    private static GrpcExampleRequest buildExampleRequest(String methodName) {
        return GrpcExampleRequest.of(GreetingServiceGrpc.SERVICE_NAME,
                                     methodName,
                                     GreetingRequest.newBuilder().setName("Armeria").build());
    }
}
