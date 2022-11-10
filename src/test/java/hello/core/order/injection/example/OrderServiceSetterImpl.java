package hello.core.order.injection.example;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.order.Order;
import hello.core.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@OrderIncludeComponent
public class OrderServiceSetterImpl implements OrderService {
    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setDiscountPolicy(@Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) {
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
