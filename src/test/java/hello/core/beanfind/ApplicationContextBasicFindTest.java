package hello.core.beanfind;

import hello.core.config.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationContextBasicFindTest {

    private AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회한다")
    void findBeanByName() {
        //given
        String beanName = "memberService";
        Class<MemberService> requiredType = MemberService.class;
        Class<MemberService> expected = MemberService.class;

        //when
        MemberService actual = ac.getBean(beanName, requiredType);

        //then
        assertThat(actual).isInstanceOf(expected);
    }

    @Test
    @DisplayName("빈 타입으로 조회한다")
    void findBeanByType() {
        //given
        Class<MemberService> beanType = MemberService.class;
        Class<MemberService> expected = MemberService.class;

        //when
        MemberService actual = ac.getBean(beanType);

        //then
        assertThat(actual).isInstanceOf(expected);
    }

    @Test
    @DisplayName("빈 타입 구현체로 조회한다")
    void findBeanByImplType() {
        //given
        Class<MemberService> beanType = MemberService.class;
        Class<MemberServiceImpl> expected = MemberServiceImpl.class;

        //when
        MemberService actual = ac.getBean(beanType);

        //then
        assertThat(actual).isInstanceOf(expected);
    }

    @Test
    @DisplayName("없는 빈 타입 이름으로 조회를 실패한다")
    void findBeanByNameX() {
        //given
        String beanTypeName = "xxx";

        //then
        Assertions.assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> {
                    //when
                    ac.getBean(beanTypeName);
                });
    }
}
