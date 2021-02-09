package CarrotMarket.CarrotMarket.controller;

import CarrotMarket.CarrotMarket.domain.Member;
import CarrotMarket.CarrotMarket.repository.MemberRepository;
import CarrotMarket.CarrotMarket.service.MailSender;
import CarrotMarket.CarrotMarket.service.MemberService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MemberController {

    private MemberService memberService;
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
        memberService.join(member);
        System.out.println("join : " + member.getEmail());

        return member.getEmail();
    }

    @PostMapping("/login")
    @ResponseBody
    public String loginControl(@RequestBody Member member) {
        String email = member.getEmail();
        String password = member.getPassword();
        String result = "";
        try {
            result = memberService.login(email, password);
        } catch (Exception e) {
            System.out.println("error : " + e);
        }

        return result;
    }

    @GetMapping("/register/emailcheck")
    public Boolean emailcheck(@RequestParam String email){
        Member member = memberRepository.load(email);
        if(member.getEmail() != null){
            return true;
        }
        return false;
    }
}