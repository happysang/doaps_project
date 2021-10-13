package kgu.doaps.controller;

import kgu.doaps.domain.Member;
import kgu.doaps.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
//    @RequestMapping("/")
//    public String home() {
//        log.info("home controller");
//        return "home";
//    }

    @RequestMapping("/")
    public String home(@CookieValue(name = "memberId", required = false) Long memberId, Model model){
        if (memberId == null){
            return "home";
        }

        //로그인
        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null){
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}