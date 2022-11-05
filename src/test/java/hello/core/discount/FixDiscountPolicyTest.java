package hello.core.discount;

import hello.core.grade.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FixDiscountPolicyTest {
    private DiscountPolicy discountPolicy;
    private Member member;
    private Long memberId;

    @BeforeEach
    void setUp() {
        discountPolicy = new FixDiscountPolicy();
        memberId = 1L;
    }

    @Test
    @DisplayName("VIP 는 1000원 할인이 적용되어야 한다")
    void vip_o() {
        // given
        member = new Member(memberId, "memberA", Grade.VIP);

        // then
        int actual = discountPolicy.discount(member, 20000);

        // when
        assertThat(actual).isEqualTo(1000);
    }

    @Test
    @DisplayName("VIP 가 아니면 할인이 적용되지 않아야 한다.")
    void vip_x() {
        // given
        member = new Member(memberId, "memberA", Grade.BASIC);

        // then
        int actual = discountPolicy.discount(member, 20000);

        // when
        assertThat(actual).isEqualTo(0);
    }
}