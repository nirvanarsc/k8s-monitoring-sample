import static com.linecorp.armeria.client.Clients.newClient;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.nirvanarsc.k8s.monitoring.sample.Application;
import com.github.nirvanarsc.k8s.monitoring.sample.grpc.GreetingServiceGrpc.GreetingServiceBlockingStub;
import com.github.nirvanarsc.k8s.monitoring.sample.grpc.GreetingServiceGrpc.GreetingServiceFutureStub;
import com.github.nirvanarsc.k8s.monitoring.sample.grpc.Welcome.GreetingReply;
import com.github.nirvanarsc.k8s.monitoring.sample.grpc.Welcome.GreetingRequest;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class GreetingServiceIntegrationTest {
    private static final String URI = "gproto+http://127.0.0.1:8080";

    @Test
    public void getReply() {
        final GreetingServiceBlockingStub helloService = newClient(URI, GreetingServiceBlockingStub.class);
        final GreetingReply reply = helloService.hello(GreetingRequest.newBuilder().setName("Armeria").build());
        assertEquals("Hello, Armeria!", reply.getMessage());
    }

    @Test
    public void getReplyWithDelay() {
        final GreetingServiceFutureStub helloService = newClient(URI, GreetingServiceFutureStub.class);
        final ListenableFuture<GreetingReply> future = helloService
                .lazyHello(GreetingRequest.newBuilder().setName("Armeria").build());
        final AtomicBoolean completed = new AtomicBoolean();
        Futures.addCallback(future, new FutureCallback<GreetingReply>() {
            @Override
            public void onSuccess(GreetingReply result) {
                assertEquals("Hello, Armeria!", result.getMessage());
                completed.set(true);
            }

            @Override
            public void onFailure(Throwable t) {
                throw new Error(t);
            }
        }, MoreExecutors.directExecutor());

        await().untilTrue(completed);
    }
}
