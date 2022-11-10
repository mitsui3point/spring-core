package hello.core.discount;

import hello.core.grade.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("mainDiscountPolicy")
public class RateDiscountPolicy implements DiscountPolicy {
    private int discountRate = 10;

    @Override
    public int discount(Member member, int itemPrice) {
        if (member.getGrade() == Grade.VIP) {
            return itemPrice * discountRate / 100;
        }
        return 0;
    }
}
