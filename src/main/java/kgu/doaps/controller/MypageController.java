package kgu.doaps.controller;

import kgu.doaps.domain.Address;
import kgu.doaps.domain.Member;
import kgu.doaps.domain.MemberStatus;
import kgu.doaps.domain.Order;
import kgu.doaps.domain.item.Item;
import kgu.doaps.service.ItemService;
import kgu.doaps.service.MemberService;
import kgu.doaps.service.OrderService;
import kgu.doaps.session.Login;
import kgu.doaps.session.SessionConst;
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
    private final ItemService itemService;

    @GetMapping("/mypage")
    public String mypageHome(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model,
                             HttpServletRequest request) {
        //login check
        if (loginMember == null) {
            model.addAttribute("member", new Member());
            return "home";
        }
        Member member = memberService.findOne(loginMember.getId());
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);
        // 왜 이렇게 해줬냐면 회원 정보 수정 후 세션에는 그대로 수정 전의 정보 세션이 들어있기 때문에 바뀐 DB의 세션객체를 업데이트해주기위해서.

        model.addAttribute("member", member);
        return "redirect:/";
    }

    @GetMapping("/mypage/myorder")
    public String myOrder(@Login Member loginMember, Model model) {
        //login check
        if (loginMember == null) {
            model.addAttribute("message", "로그인 후 이용하세요.");
            model.addAttribute("redirectLink", "/login");
            return "error/errorMessage";
        }
        model.addAttribute("member", loginMember);

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
        form.setPhone(member.getPhone());
        model.addAttribute("form", form);

        model.addAttribute("member", member);
        return "mypage/updateMemberForm";
    }

    @PostMapping("/mypage/{memberId}/edit")
    public String updateMember(@PathVariable("memberId") Long memberId, @ModelAttribute("form") MemberUpdateForm form) {
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        memberService.updateMember(memberId, form.getName(), address, form.getPhone());
        //★성별,폰번호 등등 추가해줄것.
        return "redirect:/mypage";
    }

    @GetMapping("/mypage/changeMemberStatus")
    public String updateOnlyMemberStatus(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
        if (loginMember.getMemberStatus().equals(MemberStatus.BUYER)) {
            memberService.updateMemberStatus(loginMember.getId(), MemberStatus.SELLER);
            model.addAttribute("message", "판매 계정으로 계정 변경 합니다.");
        }
        if (loginMember.getMemberStatus().equals(MemberStatus.SELLER)){
            memberService.updateMemberStatus(loginMember.getId(), MemberStatus.BUYER);
            model.addAttribute("message", "일반 계정으로 계정 변경 합니다.");
        }
        model.addAttribute("redirectLink", "/mypage");
        return  "error/errorMessage";
    }

    ///////판매자만 쓰는 메뉴 ///////////
    @GetMapping("/mypage/myitem")
    public String myItem(@Login Member loginMember, Model model) {
        if (loginMember == null) return "home";

        if (loginMember.getMemberStatus() == MemberStatus.BUYER) {
            model.addAttribute("message", "판매자 등록을 해야 합니다.");
            model.addAttribute("redirectLink", "/mypage");
            return "error/errorMessage";
        }
        List<Item> items = itemService.findMyItem(loginMember.getId());
        model.addAttribute("items", items);
        model.addAttribute("member", loginMember);
        return "mypage/myitem";

    }
}


