package hello.core.order.injection;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.injection.example.OrderIncludeComponent;
import hello.core.order.injection.example.OrderServiceFieldImpl;
import hello.core.order.injection.example.OrderTestAppConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceFieldImplTest {
    @Test
    void fieldInjection() {
        //given
        ApplicationContext ac = new AnnotationConfigApplicationContext(OrderTestAppConfig.class);
        MemberRepository expectedMember = ac.getBean(MemberRepository.class);
        DiscountPolicy expectedDiscountPolicy = ac.getBean(DiscountPolicy.class);
        //when
        OrderServiceFieldImpl orderServiceFieldImpl = ac.getBean("orderServiceFieldImpl", OrderServiceFieldImpl.class);
        MemberRepository actualMember = orderServiceFieldImpl.getMemberRepository();
        DiscountPolicy actualDiscountPolicy = orderServiceFieldImpl.getDiscountPolicy();
        //then
        System.out.println("expectedMember = " + expectedMember);
        System.out.println("expectedDiscountPolicy = " + expectedDiscountPolicy);
        System.out.println("actualMember = " + actualMember);
        System.out.println("actualDiscountPolicy = " + actualDiscountPolicy);
        assertThat(actualMember).isSameAs(expectedMember);
        assertThat(actualDiscountPolicy).isSameAs(expectedDiscountPolicy);
    }

    @Test
    @DisplayName("spring autowired 필드주입시 java test 시 필드주입 불가능 테스트(DI 프레임워크가 없을 경우 아무것도 할 수 없는 경우 테스트)")
    void fieldInjectionNullPointerTest() {
        //given
        OrderServiceFieldImpl orderServiceField = new OrderServiceFieldImpl();
        //then
        Assertions.assertThrows(
                NullPointerException.class,
                //when
                () -> orderServiceField.createOrder(1L, "itemName", 20000));
    }
}
