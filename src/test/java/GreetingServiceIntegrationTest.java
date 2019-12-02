import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.linecorp.k8s.monitoring.sample.Application;
import com.linecorp.k8s.monitoring.sample.thrift.Greeting;
import com.linecorp.k8s.monitoring.sample.thrift.GreetingService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class GreetingServiceIntegrationTest {
    @Autowired
    GreetingService.Iface helloService;

    @Test
    public void hello() throws Exception {
        final Greeting armerianWorld = helloService.hello("Armerian World");
        assertEquals("Hello, Armerian World!", armerianWorld.getGreeting());
    }
}
