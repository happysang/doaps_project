package kgu.doaps.controller;

import kgu.doaps.domain.Member;
import kgu.doaps.domain.Order;
import kgu.doaps.service.OrderService;
import kgu.doaps.web.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MypageController {

    private final OrderService orderService;

    @GetMapping("/mypage")
    public String mypageHome(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
        //login check
        if (loginMember == null) {
            return "home";
        }
//        model.addAttribute("member", loginMember);
        return "mypage/mypage";
    }

    @GetMapping("/mypage/myorder")
    public String myOrder(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
        //login check
        if (loginMember == null) {
            return "home";
        }
        List<Order> orders = orderService.findMyOrder(loginMember.getId());
        model.addAttribute("orders", orders);
        return "mypage/myorder";
    }
}
