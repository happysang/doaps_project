package kgu.doaps.controller;

import kgu.doaps.domain.item.Item;
import kgu.doaps.domain.item.Pepper;
import kgu.doaps.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new PepperForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(@RequestParam("img") MultipartFile files, PepperForm form) {
        try {
            String baseDir = "C:\\ServerFiles";
            String filePath = baseDir + "\\" + files.getOriginalFilename();
            files.transferTo(new File(filePath));
//            Authentication user = SecurityContextHolder.getContext().getAuthentication();
//            String sellerID = user.getName();

            Pepper pepper = new Pepper();
            pepper.setName(form.getName());
            pepper.setPrice(form.getPrice());
//            pepper.setMember(SETTINGUSERID);
            pepper.setStockQuantity(form.getStockQuantity());
            pepper.setImgUrl(filePath);
            itemService.saveItem(pepper);
            return "redirect:/items";
        } catch(Exception e) {
            e.printStackTrace();
        }

        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Pepper item = (Pepper) itemService.findOne(itemId);

        PepperForm form = new PepperForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") PepperForm form) {
        Pepper pepper = new Pepper();
        pepper.setId(form.getId());
        pepper.setName(form.getName());
        pepper.setPrice(form.getPrice());
        pepper.setStockQuantity(form.getStockQuantity());
        itemService.saveItem(pepper);
        return "redirect:/items";
    }

}
