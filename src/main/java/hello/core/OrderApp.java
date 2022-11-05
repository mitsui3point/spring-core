package hello.core;

import hello.core.config.AppConfig;
import hello.core.grade.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.order.Order;
import hello.core.order.OrderService;

public class OrderApp {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

        long memberId = 1L;
        memberService.join(new Member(memberId, "memberA", Grade.VIP));

        Order order = orderService.createOrder(memberId, "productA", 20000);

        System.out.println("order = " + order);
    }
}
