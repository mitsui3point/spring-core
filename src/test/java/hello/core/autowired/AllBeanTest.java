package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.grade.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AllBeanTest {
    @Test
    void findAllBeanAndGetFixDiscountPolicy() {
        //given
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        //when
        DiscountService discountService = ac.getBean(DiscountService.class);
        int actual = discountService.discountPrice(
                new Member(1L, "name", Grade.VIP),
                20000,
                "fixDiscountPolicy");
        //then
        assertThat(actual).isEqualTo(1000);
    }

    @Test
    void findAllBeanAndGetRateDiscountPolicy() {
        //given
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        //when
        DiscountService discountService = ac.getBean(DiscountService.class);
        int actual = discountService.discountPrice(
                new Member(1L, "name", Grade.VIP),
                10000,
                "rateDiscountPolicy");
        //then
        assertThat(actual).isEqualTo(1000);
    }

    //예를 들어서 할인 서비스를 제공하는데, 클라이언트가 할인의 종류(rate, fix)를 선택할 수 있다고
    static class DiscountService {
        private Map<String, DiscountPolicy> discountPolicyMap;
        private List<DiscountPolicy> discountPolicies;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> discountPolicyMap, List<DiscountPolicy> discountPolicies) {
            this.discountPolicyMap = discountPolicyMap;
            this.discountPolicies = discountPolicies;
            System.out.println("discountPolicyMap = " + discountPolicyMap);
            System.out.println("discountPolicies = " + discountPolicies);
        }

        public int discountPrice(Member member, int price, String discountPolicyName) {
            return this.discountPolicyMap
                    .get(discountPolicyName)
                    .discount(member, price);
        }
    }
}
