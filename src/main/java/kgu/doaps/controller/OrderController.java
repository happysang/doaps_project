package kgu.doaps.controller;

import kgu.doaps.domain.Member;
import kgu.doaps.domain.MemberStatus;
import kgu.doaps.domain.Order;
import kgu.doaps.domain.item.Item;
import kgu.doaps.domain.item.Pepper;
import kgu.doaps.repository.OrderSearch;
import kgu.doaps.service.ItemService;
import kgu.doaps.service.MemberService;
import kgu.doaps.service.OrderService;
import kgu.doaps.session.Login;
import kgu.doaps.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order/{orderId}")
    public String createForm(@Login Member loginMember,
                             @PathVariable("orderId") Long orderId, Model model){

        if (loginMember == null) {
            model.addAttribute("message", "로그인 후 이용하세요.");
            model.addAttribute("redirectLink", "/login");
            return "error/errorMessage";
        }

        Pepper item = (Pepper) itemService.findOne(orderId);
        model.addAttribute("item", item);
        model.addAttribute("member", loginMember);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,  //@RequestParam("html select태그의 name부분에 해당)
                        @RequestParam("itemId") Long itemId, //여기서는 파라미터만 넘겨줘서 영속 개체인 상태에서 처리를 할 수 있게 한다.
                        @RequestParam("count") int count){
        orderService.order(memberId, itemId, count);
        return "redirect:/";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model){
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/";
    }

}
