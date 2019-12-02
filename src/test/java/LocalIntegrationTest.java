import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.linecorp.armeria.client.Clients;
import com.linecorp.k8s.monitoring.sample.thrift.Greeting;
import com.linecorp.k8s.monitoring.sample.thrift.GreetingService;

// Need to have the app running locally for the test to pass.
public class LocalIntegrationTest {
    GreetingService.Iface helloService;

    @BeforeEach
    public void setUp() {
        helloService = Clients.newClient("tbinary+http://127.0.0.1:8080/thrift/greeting",
                                         GreetingService.Iface.class);
    }

    @Test
    public void hello() throws Exception {
        final Greeting armerianWorld = helloService.hello("Armerian World");
        assertEquals("Hello, Armerian World!", armerianWorld.getGreeting());
    }
}
