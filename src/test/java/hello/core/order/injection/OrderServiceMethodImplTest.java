package hello.core.order.injection;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.order.injection.example.OrderServiceMethodImpl;
import hello.core.order.injection.example.OrderTestAppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceMethodImplTest {
    @Test
    void methodInjectionTest() {
        //given
        ApplicationContext ac = new AnnotationConfigApplicationContext(OrderTestAppConfig.class);
        MemberRepository expectedMember = ac.getBean(MemberRepository.class);
        DiscountPolicy expectedDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        //when
        OrderServiceMethodImpl orderServiceMethodImpl = ac.getBean("orderServiceMethodImpl", OrderServiceMethodImpl.class);
        MemberRepository actualMember = orderServiceMethodImpl.getMemberRepository();
        DiscountPolicy actualDiscountPolicy = orderServiceMethodImpl.getDiscountPolicy();
        //then
        System.out.println("expectedMember = " + expectedMember);
        System.out.println("expectedDiscountPolicy = " + expectedDiscountPolicy);
        System.out.println("actualMember = " + actualMember);
        System.out.println("actualDiscountPolicy = " + actualDiscountPolicy);
        assertThat(actualMember).isSameAs(expectedMember);
        assertThat(actualDiscountPolicy).isSameAs(expectedDiscountPolicy);
    }
}
