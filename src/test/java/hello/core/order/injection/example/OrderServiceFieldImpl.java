package hello.core.order.injection.example;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.order.Order;
import hello.core.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@OrderIncludeComponent
public class OrderServiceFieldImpl implements OrderService {
    @Autowired private MemberRepository memberRepository;//안티패턴, 외부에서 변경이 불가능해서 테스트 하기 힘들다는 치명적인 단점이 있다.
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
