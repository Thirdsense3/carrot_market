package CarrotMarket.CarrotMarket.controller;

import CarrotMarket.CarrotMarket.domain.Member;
import CarrotMarket.CarrotMarket.service.MailSender;
import CarrotMarket.CarrotMarket.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
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
}