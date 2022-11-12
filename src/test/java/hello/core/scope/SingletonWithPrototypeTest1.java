package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {
    @Test
    void prototypeFind() {
        //given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        //when
        prototypeBean1.addCount();
        int prototypeBean1Count = prototypeBean1.getCount();
        int prototypeBean1CountExpected = 1;

        prototypeBean2.addCount();
        int prototypeBean2Count = prototypeBean2.getCount();
        int prototypeBean2CountExpected = 1;
        //then
        assertThat(prototypeBean1Count).isEqualTo(prototypeBean1CountExpected);
        assertThat(prototypeBean2Count).isEqualTo(prototypeBean2CountExpected);
    }

    @Test
    void singletonClientUsePrototype() {
        //given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class, ClientBean.class);
        //when
        int clientBean1Expected = 1;
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int clientBean1Count = clientBean1.logic();

        int clientBean2Expected = 1;
        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int clientBean2Count = clientBean2.logic();
        //then
        assertThat(clientBean1Count).isEqualTo(clientBean1Expected);
        assertThat(clientBean2Count).isEqualTo(clientBean2Expected);
    }

    @Scope("singleton")
    static class ClientBean {
        private ApplicationContext ac;

        @Autowired
        public ClientBean(ApplicationContext ac) {
            this.ac = ac;
        }

        public int logic() {
            PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count;

        public void addCount() {
            this.count++;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
            this.count = 0;
        }

    }
}