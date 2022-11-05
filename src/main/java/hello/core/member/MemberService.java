package hello.core.member;

public interface MemberService {
    /**
     * 회원 가입
     * @param member
     */
    void join(Member member);

    /**
     * 회원 단건 조회
     * @param id
     * @return 회원 단건
     */
    Member findMember(Long id);
}
