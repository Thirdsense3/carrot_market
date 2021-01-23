package CarrotMarket.CarrotMarket.repository;

import CarrotMarket.CarrotMarket.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Member load(String Email);
    Optional<Member> findByEmail(String Email);
}
