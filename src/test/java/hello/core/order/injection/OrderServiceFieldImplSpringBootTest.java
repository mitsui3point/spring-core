package hello.core.order.injection;

import hello.core.grade.Grade;
import hello.core.member.Member;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceFieldImplSpringBootTest {
    @Autowired
    private MemberServiceImpl memberServiceImpl;
    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @Test
    void fieldInjectionInBootTest() {
        //given
        //then
        Assertions.assertDoesNotThrow(() -> {
            //when
            memberServiceImpl.join(new Member(1L, "memberA", Grade.BASIC));
            orderServiceImpl.createOrder(1L, "itemName", 20000);
        });
    }
}
