package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {
    private MemberRepository memberRepository = new MemoryMemberRepository();

    private DiscountPolicy discountPolicy = new RateDiscountPolicy();
//    private DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        return new Order(member.getId(),
                itemName,
                itemPrice,
                discountPolicy.discount(member, itemPrice));
    }
}
