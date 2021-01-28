package CarrotMarket.CarrotMarket.service;

import CarrotMarket.CarrotMarket.domain.Member;
import CarrotMarket.CarrotMarket.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterService{

    private final MemberRepository memberRepository;

    @Autowired
    public RegisterService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void id_check(String emailId){

    }

    public void join(Optional<Member> member){

    }
}
