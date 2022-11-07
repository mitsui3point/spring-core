package hello.core.singleton;

import hello.core.config.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {
    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        //given
        AppConfig appConfig = new AppConfig();
        
        //when
        MemberService memberService1 = appConfig.memberService(); // 1. 조회: 호출할 때 마다 객체 생성
        MemberService memberService2 = appConfig.memberService(); // 2. 조회: 호출할 때 마다 객체 생성

        //then
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        //given
        //private 으로 생성자를 막아두었다. 컴파일 오류가 발생.
        //new SingletonService();

        //when
        SingletonService singletonService1 = SingletonService.getInstance(); // 1. 조회: 호출할 때 마다 같은 객체를 반환
        SingletonService singletonService2 = SingletonService.getInstance(); // 2. 조회: 호출할 때 마다 같은 객체를 반환

        //then
        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);
        assertThat(singletonService1).isSameAs(singletonService2); // singletonService1 == singletonService2; 참조가 같은 경우, equal; value 가 같은 경우

        singletonService1.logic();
    }
}
