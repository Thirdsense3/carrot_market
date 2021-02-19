package CarrotMarket.CarrotMarket.service;

import CarrotMarket.CarrotMarket.domain.Member;
import CarrotMarket.CarrotMarket.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class MemberService {

    JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member login(String Email, String password) throws JsonProcessingException {
        // 로그인
        AtomicBoolean attempt = new AtomicBoolean(false);
        AtomicBoolean compare = new AtomicBoolean(false);
        Optional<Member> member = memberRepository.findByEmail(Email);
        member.ifPresent( value -> {if(value.getPassword().equals(password)) {
            attempt.set(true);
        } });

        String result;

        if(!attempt.get()) {
            Member e = new Member();
            e.setEmail("error");
            return e;
        }

        return member.get();
    }

    public StringBuffer sendCertificationMail(String Email) {

        MailSender mailSender = new MailSender();
        StringBuffer Message = new StringBuffer();
        Random rnd = new Random();

        // 랜덤문자열 생성
        for(int i=0; i<8; i++) {
            int rIndex = rnd.nextInt(2);
            switch(rIndex) {
                case 0:
                    // A-Z
                    Message.append((char) ((int) (rnd.nextInt(26))+65));
                    break;
                case 1:
                    // 0-9
                    Message.append((rnd.nextInt(10)));
                    break;
            }
        }

        System.out.println("코드 : " + Message);
        // 인증메일 발송
        try {
            mailSender.sendMail(Email, Message, javaMailSender);
        } catch (Exception e) {
            System.out.println("fail");
        }

        return Message;
    }

    public boolean id_check(String email){
        if(memberRepository.findByEmail(email).isPresent()){
            return true;
        }
        return false;
    }

    public boolean nickname_check(String nickname){
        if(memberRepository.findByNickName(nickname).isPresent()){
            return true;
        }
        return false;

    }

    public void join(Member member) {
        System.out.println("저장소에 저장");
        memberRepository.save(member);
    }
}
