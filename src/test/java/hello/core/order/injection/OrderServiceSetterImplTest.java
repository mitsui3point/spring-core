package hello.core.order.injection;

import hello.core.AutoAppConfig;
import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.grade.Grade;
import hello.core.member.*;
import hello.core.order.Order;
import hello.core.order.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceSetterImplTest {
    @Test
    @DisplayName("세터주입 자바만 이용한 테스트, 내부 필드 인스턴스할당 누락")
    void setterInjectionPureJavaTest() {
        //given
        MemberRepository memberRepository = new MemoryMemberRepository();
        RateDiscountPolicy discountPolicy = new RateDiscountPolicy();
        MemberService memberService = new MemberServiceImpl(memberRepository);
        OrderServiceSetterImpl orderService = new OrderServiceSetterImpl();
        orderService.setMemberRepository(memberRepository);
        orderService.setDiscountPolicy(discountPolicy);
        int expected = 1000;
        //when
        memberService.join(new Member(1L, "userA", Grade.VIP));
        int actual = orderService.createOrder(1L, "itemNameA", 10000).getDiscountPrice();
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("세터주입 자바만 이용한 테스트(내부 필드 인스턴스할당 누락)")
    void setterInjectionPureJavaFailTest() {
        //given
        MemberRepository memberRepository = new MemoryMemberRepository();
        MemberService memberService = new MemberServiceImpl(memberRepository);
        OrderServiceSetterImpl orderService = new OrderServiceSetterImpl();
        int expected = 1000;
        //when
        memberService.join(new Member(1L, "userA", Grade.VIP));
        //then
        Assertions.assertThrows(
                NullPointerException.class,
                () -> orderService.createOrder(1L, "itemNameA", 10000).getDiscountPrice());
    }

    @Test
    void setterInjectionTest() {
        //given
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, OrderServiceSetterImpl.class);
        MemberRepository expectedMember = ac.getBean(MemberRepository.class);
        DiscountPolicy expectedDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        //when
        OrderServiceSetterImpl orderServiceSetterImpl = ac.getBean(OrderServiceSetterImpl.class);
        MemberRepository actualMember = orderServiceSetterImpl.getMemberRepository();
        DiscountPolicy actualDiscountPolicy = orderServiceSetterImpl.getDiscountPolicy();
        //then
        System.out.println("expectedMember = " + expectedMember);
        System.out.println("expectedDiscountPolicy = " + expectedDiscountPolicy);
        System.out.println("actualMember = " + actualMember);
        System.out.println("actualDiscountPolicy = " + actualDiscountPolicy);
        assertThat(actualMember).isSameAs(expectedMember);
        assertThat(actualDiscountPolicy).isSameAs(expectedDiscountPolicy);
    }

    static class OrderServiceSetterImpl implements OrderService {
        private MemberRepository memberRepository;
        private DiscountPolicy discountPolicy;

        @Autowired
        public void setMemberRepository(MemberRepository memberRepository) {
            this.memberRepository = memberRepository;
        }

        @Autowired
        public void setDiscountPolicy(@MainDiscountPolicy DiscountPolicy discountPolicy) {
            this.discountPolicy = discountPolicy;
        }

        @Override
        public Order createOrder(Long memberId, String itemName, int itemPrice) {
            Member member = memberRepository.findById(memberId);
            return new Order(member.getId(),
                    itemName,
                    itemPrice,
                    discountPolicy.discount(member, itemPrice));
        }

        public MemberRepository getMemberRepository() {
            return memberRepository;
        }

        public DiscountPolicy getDiscountPolicy() {
            return discountPolicy;
        }
    }
}
