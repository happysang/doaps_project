package kgu.doaps.controller;

import kgu.doaps.domain.Address;
import kgu.doaps.domain.Member;
import kgu.doaps.domain.Order;
import kgu.doaps.service.MemberService;
import kgu.doaps.service.OrderService;
import kgu.doap.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MypageController {

    private final OrderService orderService;
    private final MemberService memberService;

    @GetMapping("/mypage")
    public String mypageHome(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model,
                             HttpServletRequest request) {
        //login check
        if (loginMember == null) {
            return "home";
        }

        Member member = memberService.findOne(loginMember.getId());
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);
        // 왜 이렇게 해줬냐면 회원 정보 수정 후 세션에는 그대로 수정 전의 정보 세션이 들어있기 때문에 바뀐 DB의 세션객체를 업데이트해주기위해서.

        model.addAttribute("member", member);

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

    @GetMapping("/mypage/{memberId}/edit")
    public String updateMemberForm(@PathVariable("memberId") Long memberId, Model model) {
        Member member = memberService.findOne(memberId);

        MemberUpdateForm form = new MemberUpdateForm();

        form.setId(member.getId());
        form.setName(member.getName());
        form.setCity(member.getAddress().getCity());
        form.setStreet(member.getAddress().getStreet());
        form.setZipcode(member.getAddress().getZipcode());
        //★성별,폰번호 등등 추가해줄것.
        model.addAttribute("form", form);
        return "mypage/updateMemberForm";
    }

    @PostMapping("/mypage/{memberId}/edit")
    public String updateMember(@PathVariable("memberId") Long memberId, @ModelAttribute("form") MemberUpdateForm form) {

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        memberService.updateMember(memberId, form.getName(), address);
        //★성별,폰번호 등등 추가해줄것.
        return "redirect:/mypage";
    }
}