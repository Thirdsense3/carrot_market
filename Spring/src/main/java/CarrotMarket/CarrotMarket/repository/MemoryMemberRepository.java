package CarrotMarket.CarrotMarket.repository;

import CarrotMarket.CarrotMarket.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static Map<String, Member> store = new HashMap<>();

    @Override
    public Member save(Member member) {
        store.put(member.getEmail(), member);
        return member;
    }

    @Override
    public Member load(String email){
        return store.get(email);
    }

    @Override
    public Optional<Member> findByEmail(String Email) {
        return Optional.ofNullable(store.get(Email));
    }
}
