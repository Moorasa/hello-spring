package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final MemberService memberService;
    @Autowired
    public HomeController(MemberService memberService) {
        this.memberService = memberService;
    }

//    @GetMapping("/")
//    public String home() {
//        return "home";
//    }
    @GetMapping("/")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "home";
    }
}
