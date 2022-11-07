package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationContextExtendsFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면, 중복 오류가 발생한다")
    void findBeanByParentTypeDuplicate() {
        //given
        Class<DiscountPolicy> parentType = DiscountPolicy.class;

        //then
        Assertions.assertThrows(
                NoUniqueBeanDefinitionException.class,
                () -> {
                    //when
                    ac.getBean(parentType);
                });
    }

    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면, 빈 이름을 지정하면 된다")
    void findBeanByParentTypeBeanName() {
        //given
        Class<DiscountPolicy> parentType = DiscountPolicy.class;
        Class<RateDiscountPolicy> expected = RateDiscountPolicy.class;

        //when
        DiscountPolicy actual = ac.getBean("rateDiscountPolicy", parentType);

        //then
        assertThat(actual).isInstanceOf(expected);
    }

    @Test
    @DisplayName("특정 하위 타입으로 조회")
    void findBeanBySubType() {
        //given
        Class<RateDiscountPolicy> subType = RateDiscountPolicy.class;
        Class<RateDiscountPolicy> expected = RateDiscountPolicy.class;

        //when
        DiscountPolicy actual = ac.getBean(subType);

        //then
        assertThat(actual).isInstanceOf(expected);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회")
    void findBeanByParentType() {
        //given
        Class<DiscountPolicy> parentType = DiscountPolicy.class;
        int expected = 2;

        //when
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(parentType);
        int actual = beansOfType.size();

        //then
        for (String beanName : beansOfType.keySet()) {
            System.out.println("beanName = " + beanName +
                    ", beansOfType.get(beanName) = " + beansOfType.get(beanName).toString());
        }
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회; Object")
    void findBeanByObjectType() {
        //given
        Class<Object> parentType = Object.class;

        //when
        Map<String, Object> beansOfType = ac.getBeansOfType(parentType);
        int actual = beansOfType.size();

        //then
        for (String beanName : beansOfType.keySet()) {
            System.out.println("beanName = " + beanName +
                    ", beansOfType.get(beanName) = " + beansOfType.get(beanName).toString());
        }
    }

    @Configuration
    static class TestConfig {
        @Bean
        DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }

        @Bean
        DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }
    }
}
