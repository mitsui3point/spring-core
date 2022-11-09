package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.config.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class AutoAppConfigTest {
    @Test
    void basicScan() {
        //given
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        //when
        MemberService memberService = ac.getBean(MemberService.class);
        //then
        assertThat(memberService.getClass().toString()).contains("MemberServiceImpl");
    }

    @Test
    void basicScanAll() {
        //given
        AnnotationConfigApplicationContext acAuto = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        AnnotationConfigApplicationContext acPrev = new AnnotationConfigApplicationContext(AppConfig.class);
        //when
        String[] prevBeanDefinitionNames = acPrev.getBeanDefinitionNames();
        //then
        for (String prevBeanDefinitionName : prevBeanDefinitionNames) {
            compareBetweenAutoAndPrevAppConfig(acAuto, acPrev, prevBeanDefinitionName);
        }
    }

    private void compareBetweenAutoAndPrevAppConfig(AnnotationConfigApplicationContext acAuto,
                                                    AnnotationConfigApplicationContext acPrev,
                                                    String prevBeanDefinitionName) {
        if (isBeanRegisteredByUser(acPrev, prevBeanDefinitionName)) {
            Assertions.assertDoesNotThrow(() -> {
                acAuto.getBeansOfType(
                        acPrev.getType(prevBeanDefinitionName));
            });
        }
    }

    private boolean isBeanRegisteredByUser(AnnotationConfigApplicationContext ac,
                                           String definitionName) {
        return ac.getBeanDefinition(definitionName).getRole() == BeanDefinition.ROLE_APPLICATION &&
                !ac.getBean(definitionName).getClass().toString().contains("AppConfig");
    }
}
