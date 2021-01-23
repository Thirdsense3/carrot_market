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
        //TODO memberRepository.findByEmail로 NULL 검사
        Member member = memberRepository.load(emailId);
        if(member.getEmail() != null){
            //TODO 중복 이메일 존재
        }
        else{
            join(member);
        }
    }

    public void join(Member member){
        //TODO 중복검사 후 입력받은 멤버 클래스 저장
        memberRepository.save(member);
    }
}
