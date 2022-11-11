package hello.core.order.injection;

import hello.core.AutoAppConfig;
import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
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

public class OrderServiceFieldImplTest {
    @Test
    @DisplayName("필드주입 자바만 이용한 테스트 불가능")
    void fieldInjectionPureJavaNotAbleTest() {
        //given
        MemberRepository memberRepository = new MemoryMemberRepository();
        MemberService memberService = new MemberServiceImpl(memberRepository);
        OrderServiceFieldImpl orderService = new OrderServiceFieldImpl();
        int expected = 1000;
        //when
        memberService.join(new Member(1L, "userA", Grade.VIP));
        //then
        Assertions.assertThrows(
                NullPointerException.class,
                () -> orderService.createOrder(1L, "itemNameA", 10000).getDiscountPrice());
    }
    @Test
    void fieldInjection() {
        //given
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, OrderServiceFieldImpl.class);
        MemberRepository expectedMember = ac.getBean(MemberRepository.class);
        DiscountPolicy expectedDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        //when
        OrderServiceFieldImpl orderServiceFieldImpl = ac.getBean(OrderServiceFieldImpl.class);
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

    static class OrderServiceFieldImpl implements OrderService {
        @Autowired
        private MemberRepository memberRepository;//안티패턴, 외부에서 변경이 불가능해서 테스트 하기 힘들다는 치명적인 단점이 있다.
        @Autowired private @MainDiscountPolicy DiscountPolicy discountPolicy;

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
