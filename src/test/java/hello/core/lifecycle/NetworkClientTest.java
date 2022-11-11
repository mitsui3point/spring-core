package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class NetworkClientTest {
    @Test
    void networkClientConnDisConnTest() {
        //given
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(NetworkClientConfig.class);
        //when
        ac.getBean(NetworkClient.class);
        ac.close();
        //then
    }

    @Configuration
    static class NetworkClientConfig {
        @Bean
        public NetworkClient networkClient() {
            System.out.println("NetworkClientConfig.networkClient");
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://spring.test.dev");
            return networkClient;
        }
    }
}
