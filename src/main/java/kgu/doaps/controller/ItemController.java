package kgu.doaps.controller;

import kgu.doaps.domain.Member;
import kgu.doaps.domain.MemberStatus;
import kgu.doaps.domain.Order;
import kgu.doaps.domain.OrderStatus;
import kgu.doaps.domain.item.Item;
import kgu.doaps.domain.item.Pepper;
import kgu.doaps.service.ItemService;
import kgu.doaps.service.OrderService;
import kgu.doaps.session.Login;
import kgu.doaps.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final OrderService orderService;

    @GetMapping("/items/new")
    public String createForm(@Login Member loginMember, Model model) {
        if (loginMember == null) {
            model.addAttribute("message", "로그인 후 이용하세요.");
            model.addAttribute("redirectLink", "/login");
            return "error/errorMessage";
        }

        if (loginMember.getMemberStatus() == MemberStatus.BUYER) {
            model.addAttribute("message", "판매자 등록을 해야 합니다.");
            model.addAttribute("redirectLink", "/mypage");
            return "error/errorMessage";
        }
        model.addAttribute("form", new PepperForm());
        model.addAttribute("member", loginMember);
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(@RequestParam("img") MultipartFile files, PepperForm form, HttpServletRequest request,
                         @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {
        try {
            String root_path = request.getSession().getServletContext().getRealPath("/");
            String attach_path = "upload/";
            String filename = files.getOriginalFilename();
            File f = new File(root_path + attach_path + filename);
            files.transferTo(f);

            Pepper pepper = new Pepper();
            pepper.setName(form.getName());
            pepper.setPrice(form.getPrice());
            pepper.setMember(loginMember);
            pepper.setStockQuantity(form.getStockQuantity());
            pepper.setImgUrl(attach_path+filename);

            pepper.setImportDate(form.getImportDate());
            pepper.setProcessDate(form.getProcessDate());
            pepper.setOrigin(form.getOrigin());
            pepper.setVariety(form.getVariety());
            pepper.setColor(form.getColor());
            pepper.setSpicy(form.getSpicy());

            itemService.saveItem(pepper);
            return "redirect:/";
        } catch(Exception e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@Login Member loginMember, @PathVariable("itemId") Long itemId, Model model) {
        if (loginMember == null) {
            model.addAttribute("message", "로그인 후 이용하세요.");
            model.addAttribute("redirectLink", "/login");
            return "error/errorMessage";
        }
        model.addAttribute("member", loginMember);

        Pepper item = (Pepper) itemService.findOne(itemId);

        PepperForm form = new PepperForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setImgUrl(item.getImgUrl());

        form.setImportDate(item.getImportDate());
        form.setProcessDate(item.getProcessDate());
        form.setOrigin(item.getOrigin());
        form.setVariety(item.getVariety());
        form.setColor(item.getColor());
        form.setSpicy(item.getSpicy());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") PepperForm form,
                             @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {
        Pepper pepper = new Pepper();
        pepper.setId(form.getId());
        pepper.setName(form.getName());
        pepper.setPrice(form.getPrice());
        pepper.setMember(loginMember);
        pepper.setStockQuantity(form.getStockQuantity());

        pepper.setImportDate(form.getImportDate());
        pepper.setProcessDate(form.getProcessDate());
        pepper.setOrigin(form.getOrigin());
        pepper.setVariety(form.getVariety());
        pepper.setColor(form.getColor());
        pepper.setSpicy(form.getSpicy());

        itemService.saveItem(pepper);
        return "redirect:/";
    }

    /*
    통계
     */
    @GetMapping("/items/{itemId}/stats")
    public String itemStats(@Login Member loginMember, @PathVariable("itemId") Long itemId, Model model) {
        if (loginMember == null) {
            model.addAttribute("message", "로그인 후 이용하세요.");
            model.addAttribute("redirectLink", "/login");
            return "error/errorMessage";
        }
        model.addAttribute("member", loginMember);

        // 1. 아이템 정보 가져오기
        Pepper item = (Pepper) itemService.findOne(itemId);
        // 2. 아이템을 산 Order 에서 Order 정보 싹 가져온 후 CANCEL된애들 빼주기
        List<Order> orders = orderService.findByItem(itemId);
        orders.removeIf(order -> (order.getStatus() == OrderStatus.CANCEL)); //취소된 Order 빼주기 람다식

        //3. 종합통계는  따로 구해서 해주기
        int totalSales = item.getSales();
        int totalMoney=0;
        for (Order order : orders) {
            totalMoney+=order.getTotalPrice();
        }


        model.addAttribute("orders", orders);
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalMoney", totalMoney);
        return "items/itemStats";
    }

    @GetMapping(value = "/items/{id}/detail")
    public String itemDetail(@Login Member loginMember, @PathVariable("id") Long itemId, Model model){
        Pepper item = (Pepper) itemService.findOne(itemId);
        model.addAttribute("item", item);
        model.addAttribute("member",loginMember);

        if (loginMember == null) model.addAttribute("member", new Member());
        else model.addAttribute("member", loginMember);

        return "items/itemDetail";
    }

    @GetMapping("/items/readall")
    public String itemAll(@Login Member loginMember, Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        if (loginMember == null) model.addAttribute("member", new Member());
        else model.addAttribute("member", loginMember);
        return "items/itemAll";
    }
}
