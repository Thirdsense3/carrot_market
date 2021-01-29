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

    @Autowired
    JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String login(String Email, String password) throws JsonProcessingException {
        // 로그인
        AtomicBoolean attempt = new AtomicBoolean(false);
        AtomicBoolean compare = new AtomicBoolean(false);
        Optional<Member> member = memberRepository.findByEmail(Email);
        member.ifPresent( value -> {if(value.getPassword().equals(password)) {
            attempt.set(true);
            compare.set(true);
        } else {
            attempt.set(true);
        }});

        String result;

        if(attempt.get()) {
            if(compare.get()) {
                ObjectMapper objectMapper = new ObjectMapper();
                result = objectMapper.writeValueAsString(member.get());
            } else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("error", "wrong password");
                jsonObject.put("email", "error");
                result = jsonObject.toString();
            }
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error", "invalid email");
            jsonObject.put("email", "error");
            result = jsonObject.toString();
        }
        return result;
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

        // 인증메일 발송
        try {
            mailSender.sendMail(Email, Message, javaMailSender);
        } catch (Exception e) {
            System.out.println("fail");
        }

        return Message;
    }

    public void join(Member member) {
        memberRepository.save(member);
    }
}
