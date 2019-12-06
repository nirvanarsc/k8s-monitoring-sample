package com.github.nirvanarsc.k8s.monitoring.sample.service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.context.annotation.Configuration;

import com.github.nirvanarsc.k8s.monitoring.sample.grpc.GreetingServiceGrpc.GreetingServiceImplBase;
import com.github.nirvanarsc.k8s.monitoring.sample.grpc.Welcome.GreetingReply;
import com.github.nirvanarsc.k8s.monitoring.sample.grpc.Welcome.GreetingRequest;

import com.linecorp.armeria.server.ServiceRequestContext;

import io.grpc.stub.StreamObserver;

@Configuration
public class GreetingService extends GreetingServiceImplBase {

    private static final AtomicLong ID = new AtomicLong();

    /**
     * Sends a {@link GreetingReply} immediately when receiving a request.
     */
    @Override
    public void hello(GreetingRequest request, StreamObserver<GreetingReply> responseObserver) {
        responseObserver.onNext(buildReply(request.getName()));
        responseObserver.onCompleted();
    }

    /**
     * Sends a {@link GreetingReply} 3 seconds after receiving a request.
     */
    @Override
    public void lazyHello(GreetingRequest request, StreamObserver<GreetingReply> responseObserver) {
        ServiceRequestContext.current().contextAwareEventLoop().schedule(() -> {
            responseObserver.onNext(buildReply(request.getName()));
            responseObserver.onCompleted();
        }, 3, TimeUnit.SECONDS);
    }

    private static GreetingReply buildReply(String message) {
        return GreetingReply.newBuilder()
                            .setMessage("Hello, " + message + '!')
                            .setId(ID.incrementAndGet())
                            .build();
    }
}
