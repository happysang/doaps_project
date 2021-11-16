package kgu.doaps.controller;

import kgu.doaps.domain.Member;
import kgu.doaps.domain.item.Item;
import kgu.doaps.service.ItemService;
import kgu.doaps.session.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final ItemService itemService;

    @GetMapping("/")
    public String home(@Login Member loginMember, Model model){

        List<Item> items = itemService.findItems();
        Collections.reverse(items);

        model.addAttribute("items", items);

        //세션에 회원 데이터가 없으면 아무것도 없는 member 만든 후 리턴
        if (loginMember == null){
            model.addAttribute("member", new Member());
            return "home";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "home";
    }
}