package hello.core.beanfind;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationContextSameBeanFindTest {

    private AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다")
    void findBeanByTypeDuplicate() {
        //given
        Class<MemberRepository> beanType = MemberRepository.class;

        //when, then
        Assertions.assertThrows(
                NoUniqueBeanDefinitionException.class,
                () -> {
                    ac.getBean(beanType);
                });
    }

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 빈 이름을 지정하면 된다")
    void findBeanByName() {
        //given
        String beanName = "memberRepository1";
        Class<MemberRepository> beanType = MemberRepository.class;
        Class<MemberRepository> expected = MemberRepository.class;

        //when
        MemberRepository actual = ac.getBean(beanName, beanType);

        //then
        assertThat(actual).isInstanceOf(expected);
    }

    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    void findAllBeanByName() {
        //given
        Class<MemberRepository> beanType = MemberRepository.class;
        int expected = 2;

        //when
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(beanType);
        int actual = beansOfType.size();

        //then
        for (String key : beansOfType.keySet()) {
            System.out.println(
                    "key = " + key +
                    ", value = " + beansOfType.get(key));
        }
        assertThat(actual).isEqualTo(expected);
    }

    @Configuration
    static class SameBeanConfig {
        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }
}
