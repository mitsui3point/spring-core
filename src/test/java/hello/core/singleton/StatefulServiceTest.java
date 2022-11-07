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
        int expected = 20000;

        //when
        statefulService1.order("userA", 10000);//ThreadA: A사용자가 10000원 주문
        statefulService2.order("userB", 20000);//ThreadB: B사용자가 20000원 주문
        int actual = statefulService1.getPrice();

        //then
        System.out.println("price = " + actual);
        assertThat(actual).isEqualTo(expected);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}