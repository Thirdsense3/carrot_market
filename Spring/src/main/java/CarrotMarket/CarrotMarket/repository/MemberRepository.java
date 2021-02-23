package CarrotMarket.CarrotMarket.repository;

import CarrotMarket.CarrotMarket.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    List<Member> load();
    Optional<Member> findByEmail(String Email);
    Optional<Member> findByNickName(String nickname);
}
