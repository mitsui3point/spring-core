package hello.core.order.injection;

import hello.core.AutoAppConfig;
import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.order.Order;
import hello.core.order.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceMethodImplTest {
    @Test
    void methodInjectionTest() {
        //given
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, OrderServiceMethodImpl.class);
        MemberRepository expectedMember = ac.getBean(MemberRepository.class);
        DiscountPolicy expectedDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        //when
        OrderServiceMethodImpl orderServiceMethodImpl = ac.getBean(OrderServiceMethodImpl.class);
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

    static class OrderServiceMethodImpl implements OrderService {
        private MemberRepository memberRepository;//안티패턴, 외부에서 변경이 불가능해서 테스트 하기 힘들다는 치명적인 단점이 있다.
        private DiscountPolicy discountPolicy;

        @Autowired
        public void init(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
            this.memberRepository = memberRepository;
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
