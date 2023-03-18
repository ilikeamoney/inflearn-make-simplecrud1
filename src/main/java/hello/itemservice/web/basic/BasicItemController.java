package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // lombok 에서 필드에 final 이 붙은 멤버변수들을 자동으로 생성자를 만들어줌
                        // 생성자가 한개라면 Spring 에서 자동으로 빈으로 등록 시켜줌
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "/basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {

        itemRepository.save(item);
//      model.addAttribute("item", item); // @ModelAttribute 를 사용하면 자동 생성 생략 가능

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {

//      Item -> item 클래스명의 앞글자를 소문자로 변경한다. 그러고 ModelAttribute 에 담긴다.
        itemRepository.save(item);
//      model.addAttribute("item", item); // @ModelAttribute 를 사용하면 자동 생성 생략 가능

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV4(Item item) {

//      Item -> item 클래스명의 앞글자를 소문자로 변경한다. 그러고 ModelAttribute 에 담긴다.
        itemRepository.save(item);
//      model.addAttribute("item", item); // @ModelAttribute 를 사용하면 자동 생성 생략 가능

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV5(Item item) {

//      Item -> item 클래스명의 앞글자를 소문자로 변경한다. 그러고 ModelAttribute 에 담긴다.
        itemRepository.save(item);
//      model.addAttribute("item", item); // @ModelAttribute 를 사용하면 자동 생성 생략 가능

        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
//      Item -> item 클래스명의 앞글자를 소문자로 변경한다. 그러고 ModelAttribute 에 담긴다.
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
//      model.addAttribute("item", item); // @ModelAttribute 를 사용하면 자동 생성 생략 가능

        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";

    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }


    /**
     *  테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("ItemA", 10000, 10));
        itemRepository.save(new Item("ItemB", 20000, 20));
    }
}
