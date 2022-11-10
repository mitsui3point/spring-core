package hello.core.order.injection;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.grade.Grade;
import hello.core.member.*;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceImplTest {
    @Test
    @DisplayName("생성자 주입 자바만 이용한 테스트(생성자로 주입후 변경 불가, 주입시 누락 방지 효과)")
    void constructorInjectionPureJavaTest() {
        //given
        MemberRepository memberRepository = new MemoryMemberRepository();
        MemberService memberService = new MemberServiceImpl(memberRepository);
        OrderService orderService = new OrderServiceImpl(memberRepository, new RateDiscountPolicy());
        int expected = 1000;
        //when
        memberService.join(new Member(1L, "userA", Grade.VIP));
        int actual = orderService.createOrder(1L, "itemNameA", 10000).getDiscountPrice();
        //then
        assertThat(actual).isEqualTo(expected);
    }

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
