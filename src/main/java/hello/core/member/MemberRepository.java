package hello.core.member;

public interface MemberRepository {
    void join(Member member);

    Member findById(Long id);
}
