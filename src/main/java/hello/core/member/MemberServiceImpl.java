package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component//(value = "service")
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.join(member);
    }

    @Override
    public Member findMember(Long id) {
        return memberRepository.findById(id);
    }

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
