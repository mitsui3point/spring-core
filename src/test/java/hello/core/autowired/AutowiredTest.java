package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {
    @Test
    void noAutowiredTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutowiredInjectTest.class);
    }

    static class AutowiredInjectTest {
        @Autowired(required = false)
        public void set1(Member member) {
            System.out.println("AutowiredInjectTest.set1");
            System.out.println("member = " + member);
        }

        @Autowired
        public void set2(@Nullable Member member) {
            System.out.println("AutowiredInjectTest.set2");
            System.out.println("member = " + member);
        }

        @Autowired
        public void set3(Optional<Member> member) {
            System.out.println("AutowiredInjectTest.set3");
            System.out.println("member = " + member);
        }
    }
}
