package hello.core.xml;

import hello.core.config.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class XmlAppContext {
    @Test
    @DisplayName("xml 로 bean 등록")
    void xmlAppContext() {
        //given
        ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        //when
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        //then
        assertThat(memberService).isInstanceOf(MemberService.class);
        System.out.println("memberService = " + memberService);
    }
}
