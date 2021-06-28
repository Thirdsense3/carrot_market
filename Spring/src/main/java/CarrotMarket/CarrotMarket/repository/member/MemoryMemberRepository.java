package CarrotMarket.CarrotMarket.repository.member;

import CarrotMarket.CarrotMarket.domain.Member;
import CarrotMarket.CarrotMarket.repository.MemberRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    private static Map<String, Member> store = new HashMap<>();

    @Override
    public Member save(Member member) {
        store.put(member.getEmail(), member);
        return member;
    }

    @Override
    public List<Member> load(){
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.ofNullable(store.get(email));
    }

    @Override
    public Optional<Member> findByNickName(String nickname) {
        return  Optional.ofNullable(store.get(nickname));
    }
}
