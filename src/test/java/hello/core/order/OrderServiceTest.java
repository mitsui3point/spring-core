package hello.core.order;

import hello.core.config.AppConfig;
import hello.core.grade.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceTest {


    private OrderService orderService;
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        AppConfig appConfig = new AppConfig();
        orderService = appConfig.orderService();
        memberService = appConfig.memberService();
    }

    @Test
    void createOrder() {
        // given
        long memberId = 1L;
        memberService.join(new Member(memberId, "memberA", Grade.VIP));

        // when
        Order actual = orderService.createOrder(memberId, "productA", 10000);

        // then
        System.out.println("order = " + actual);
        assertThat(actual.getDiscountPrice()).isEqualTo(1000);
    }
}
