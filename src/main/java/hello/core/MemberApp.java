package hello.core;

import hello.core.config.AppConfig;
import hello.core.grade.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;

public class MemberApp {

    public static void main(String[] args) {
        MemberService memberService = new AppConfig().memberService();
        Member member = new Member(1L, "MemberA", Grade.VIP);

        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        System.out.println("member = " + member);
        System.out.println("findMember = " + findMember);
    }
    
}
