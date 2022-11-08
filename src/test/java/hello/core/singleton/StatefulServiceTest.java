package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

class StatefulServiceTest {
    @Test
    void statefulServiceSingleton() {
        //given
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);
        int expectedA = 10000;
        int expectedB = 20000;

        //when
        int userAPrice = statefulService1.order("userA", 10000);//ThreadA: A사용자가 10000원 주문
        int userBPrice = statefulService2.order("userB", 20000);//ThreadB: B사용자가 20000원 주문

        //then
        System.out.println("userAPrice = " + userAPrice);
        System.out.println("userBPrice = " + userBPrice);
        assertThat(userAPrice).isEqualTo(expectedA);
        assertThat(userBPrice).isEqualTo(expectedB);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}