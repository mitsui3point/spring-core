package hello.core.discount;

import hello.core.member.Member;

public class RateDiscountPolicy implements DiscountPolicy {
    private int productPrice;

    public RateDiscountPolicy(int productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public int discount(Member member, int productPrice) {
        return this.productPrice * (100 / 100);
    }
}
