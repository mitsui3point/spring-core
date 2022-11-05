package hello.core.order;

import hello.core.config.AppConfig;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.grade.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceTest {


    private AppConfig appConfig;
    private OrderService orderService;
    private MemberService memberService;
    private int expected;

    @BeforeEach
    void setUp() {
        appConfig = new AppConfig();
        orderService = appConfig.orderService();
        memberService = appConfig.memberService();
        expected = getExpectedDiscountPrice();
    }

    @Test
    void createOrder() {
        // given
        long memberId = 1L;
        String itemName = "productA";
        int itemPrice = 20000;
        memberService.join(new Member(memberId, "memberA", Grade.VIP));

        // when
        int actual = orderService.createOrder(
                        memberId,
                        itemName,
                        itemPrice)
                .getDiscountPrice();

        // then
        System.out.println("order = " + actual);
        assertThat(actual).isEqualTo(expected);
    }

    private int getExpectedDiscountPrice() {
        if (appConfig.discountPolicy() instanceof FixDiscountPolicy) {
            return 1000;
        }
        if (appConfig.discountPolicy() instanceof RateDiscountPolicy) {
            return 2000;
        }
        return 0;
    }
}
