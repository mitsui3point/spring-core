package hello.core.order.injection.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTestAppConfigTest {

    private String[] expected;

    @BeforeEach
    void setUp() {
        this.expected = new String[]{
                "orderTestAppConfig",
                "memberServiceImpl",
                "memoryMemberRepository",
                "rateDiscountPolicy",
                "orderServiceImpl", "orderServiceFieldImpl", "orderServiceMethodImpl", "orderServiceSetterImpl",

        };
    }

    @Test
    void orderTestAppConfigTest() {
        //given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(OrderTestAppConfig.class);
        //when
        String[] actual = ac.getBeanDefinitionNames();
        for (String beanName : actual) {
            boolean isNotRoleApplication = ac.getBeanDefinition(beanName).getRole() != BeanDefinition.ROLE_APPLICATION;
            if (isNotRoleApplication) {
                actual = Arrays.stream(actual)
                        .filter(name -> name != beanName)
                        .toArray(String[]::new);
            }
        }
        //then
        assertThat(actual).contains(this.expected);
    }
}