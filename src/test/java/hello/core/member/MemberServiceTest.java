package hello.core.member;

import hello.core.grade.Grade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberServiceTest {
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        this.memberService = new MemberServiceImpl();
    }

    @Test
    void join() {
        //given
        Member member = new Member(1L, "MemberA", Grade.VIP);

        //when
        memberService.join(member);
        Member actual = memberService.findMember(1L);

        //then
        assertThat(actual).isEqualTo(member);
    }
}
