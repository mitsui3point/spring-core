package hello.core.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

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
