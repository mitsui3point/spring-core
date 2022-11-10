package hello.core.order.injection;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceImplTest {
    @Test
    void constructorInjectionTest() {
        //given
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        MemberRepository expectedMember = ac.getBean(MemberRepository.class);
        DiscountPolicy expectedDiscountPolicy = ac.getBean(DiscountPolicy.class);
        //when
        OrderServiceImpl orderServiceImpl = ac.getBean("orderServiceImpl", OrderServiceImpl.class);
        MemberRepository actualMember = orderServiceImpl.getMemberRepository();
        DiscountPolicy actualDiscountPolicy = orderServiceImpl.getDiscountPolicy();
        //then
        System.out.println("expectedMember = " + expectedMember);
        System.out.println("expectedDiscountPolicy = " + expectedDiscountPolicy);
        System.out.println("actualMember = " + actualMember);
        System.out.println("actualDiscountPolicy = " + actualDiscountPolicy);
        assertThat(actualMember).isSameAs(expectedMember);
        assertThat(actualDiscountPolicy).isSameAs(expectedDiscountPolicy);
    }
}
