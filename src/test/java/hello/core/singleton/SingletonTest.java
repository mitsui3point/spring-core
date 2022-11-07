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
}
