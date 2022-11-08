package hello.core.singleton;

import hello.core.config.AppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationSingletonTest {
    @Test
    @DisplayName("스프링 컨테이너 싱글톤 테스트")
    void configurationTest() {
        //given
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        //when
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        //then
        System.out.println("memberService -> memberRepository1 = " + memberRepository1);
        System.out.println("orderService -> memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);
        assertThat(memberRepository).isSameAs(memberRepository1);
        assertThat(memberRepository).isSameAs(memberRepository2);
    }

    @Test
    @DisplayName("스프링 컨테이너 @Configuration 제거 테스트")
    void removeConfigureAnnotationTest() {
        //given
        ApplicationContext ac = new AnnotationConfigApplicationContext(NoConfigurationAppConfig.class);

        //when
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        //then
        System.out.println("memberService -> memberRepository1 = " + memberRepository1);
        System.out.println("orderService -> memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);
        assertThat(memberRepository).isNotSameAs(memberRepository1);
        assertThat(memberRepository).isNotSameAs(memberRepository2);
        assertThat(memberRepository1).isNotSameAs(memberRepository2);
    }

    @Test
    @DisplayName("스프링 컨테이너 @Configuration 어노테이션 객체 CGLIB 바이트코드 상속객체 생성 확인 테스트")
    void configurationDeep() {
        //given
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        String expected = "$$EnhancerBySpringCGLIB$$";//CGLIB bytecode library

        //when
        AppConfig appConfig = ac.getBean("appConfig", AppConfig.class);
        String actual = appConfig.getClass().toString();

        //then
        System.out.println("appConfig = " + appConfig);
        assertThat(actual).contains(expected);
        assertThat(appConfig).isInstanceOf(AppConfig.class);//CGLIB 을 사용한 바이트코드 객체가 AppConfig.class 의 instance 인지 확인
    }

    static class NoConfigurationAppConfig {
        @Bean
        public OrderService orderService() {
            System.out.println("call AppConfig.orderService");
            return new OrderServiceImpl(
                    memberRepository(),
                    discountPolicy());
        }

        @Bean
        public MemberService memberService() {
            System.out.println("call AppConfig.memberService");
            return new MemberServiceImpl(memberRepository());
        }

        @Bean
        public MemberRepository memberRepository() {
            System.out.println("call AppConfig.memberRepository");
            return new MemoryMemberRepository();
        }

        @Bean
        public DiscountPolicy discountPolicy() {
            return new RateDiscountPolicy();
        }
    }
}
