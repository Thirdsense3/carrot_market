package CarrotMarket.CarrotMarket.controller;

import CarrotMarket.CarrotMarket.domain.CertificationCode;
import CarrotMarket.CarrotMarket.domain.Member;
import CarrotMarket.CarrotMarket.repository.MemberRepository;
import CarrotMarket.CarrotMarket.service.MailSender;
import CarrotMarket.CarrotMarket.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.Message;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    private final MemberService memberService;
    //public MemberRepository memberRepository;
    //public MailSender mailSender;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    @ResponseBody
    public String Temp() {
        Member member = new Member();
        member.setEmail("HelloSpring");
        member.setPassword("num12345");
        member.setName("John");
        member.setNickname("wooky");
        member.setLocation(0F,0F);
        memberService.join(member);
        System.out.println("join : " + member.getEmail());

        return member.getEmail();
    }

    @PostMapping("/login")
    @ResponseBody
    public Member loginControl(Member member) {
        String email = member.getEmail();
        String password = member.getPassword();
        try {
            member = memberService.login(email, password);
            System.out.println(member.getEmail());
        } catch (Exception e) {
            System.out.println("error : " + e);
        }

        return member;
    }

    @RequestMapping(value = "/register/emailing/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Member emailing(@PathVariable String id){

        System.out.println("email : " + id);

        Member member = new Member();
        if(memberService.id_check(id)){
            member.setEmail(id);
        }
        else{
            member.setEmail(null);
        }

        return member;
    }

    @RequestMapping(value = "/register/nicknaming/{nickname}",method = RequestMethod.GET)
    @ResponseBody
    public Member nicknaming(@PathVariable String nickname){
        Member member = new Member();

        System.out.println("email : " + nickname);

        if(memberService.nickname_check(nickname)){
            member.setNickname(nickname);
        }
        else{
            member.setNickname(null);
        }

        return member;
    }

    @RequestMapping(value = "/register/member",method = RequestMethod.POST)
    @ResponseBody
    public Member join(Member member){ // TODO ("@requestbody 사용해야 하는지 확인")
        try{
            String email = member.getEmail();
            String password = member.getPassword();
            String name = member.getName();
            String nickname = member.getNickname();
            Float locationX = member.getLocationX();
            Float locationY = member.getLocationY();

            System.out.println("email : " + email);
            System.out.println("password : " + password);
            System.out.println("name : " + name);
            System.out.println("nickname : " + nickname);
            System.out.println("location : " + locationX.toString() + " " + locationY.toString());

            Member member1 = new Member(email,password,name,locationX, locationY, nickname);
            memberService.join(member1);

            return member;

        }catch (Exception e){
            System.out.println("저장중 오류 발생");
        }
        return member;
    }

    @RequestMapping(value = "/register/verifying/{email}",method = RequestMethod.GET)
    @ResponseBody
    public CertificationCode verifying(@PathVariable String email){
        System.out.println("인증용 email : " + email);
        String code = memberService.sendCertificationMail(email).toString();
        CertificationCode certificationCode = new CertificationCode(code);
        System.out.println("code : " + certificationCode.getCode());
        return certificationCode;
    }
}