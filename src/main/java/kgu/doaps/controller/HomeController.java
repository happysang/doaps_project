package kgu.doaps.controller;

import kgu.doaps.domain.Member;
import kgu.doaps.repository.MemberRepository;
import kgu.doaps.web.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final SessionManager sessionManager;

    @RequestMapping("/")
    public String home(HttpServletRequest request, Model model){
        //세션 관리자에 저장된 회원 정보 조회
        Member member = (Member)sessionManager.getSession(request);

        //로그인
        if (member == null){
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }
}