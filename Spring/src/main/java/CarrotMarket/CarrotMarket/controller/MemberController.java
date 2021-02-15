package CarrotMarket.CarrotMarket.controller;

import CarrotMarket.CarrotMarket.domain.Member;
import CarrotMarket.CarrotMarket.repository.MemberRepository;
import CarrotMarket.CarrotMarket.service.MailSender;
import CarrotMarket.CarrotMarket.service.MemberService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.Message;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    private final MemberService memberService;
    public MemberRepository memberRepository;

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
        member.setName("철수");
        member.setNickname("wooky");
        member.setLocation("인천");
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

    @RequestMapping(value = "/register/emailing/{email}",method = RequestMethod.GET)
    @ResponseBody
    public Member emailing(@PathVariable String email){
        Member member = new Member();
        if(memberService.id_check(email)){
            member.setEmail(email);
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
        if(memberService.nickname_check(nickname)){
            member.setNickname(nickname);
        }
        else{
            member.setNickname(null);
        }

        return member;
    }

    @RequestMapping(value = "/register/member",method = RequestMethod.POST)
    public Member join(HttpServletRequest request){
        Member member = new Member();
        try{
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String  name = request.getParameter("name");
            String nickname = request.getParameter("nickname");
            String location = request.getParameter("location");

            Member member1 = new Member(email,password,name,nickname,location);
            memberService.join(member1);

            return member;

        }catch (Exception e){
            logger.info("저장중 오류 발생");
        }
        return member;
    }
}