package mvc.itemservice.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mvc.itemservice.domain.Repository.ItemRepository;
import mvc.itemservice.domain.item.DeliveryCode;
import mvc.itemservice.domain.item.Item;
import mvc.itemservice.domain.item.ItemType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    // 등록지역
    @ModelAttribute("regions")
    public Map<String, String> regions() {

        Map<String, String> regions = new LinkedHashMap<>(); // 해시맵은 순서가 보장이 안되서 링크드해시맵 사용

        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");

        return regions;
    }

    // 상품 종류
    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {

        return ItemType.values();
    }

    // 배송 방식
    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {

        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));

        return deliveryCodes;
    }

    // 테스트용 가데이터 추가
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10,
                true, "SEOUL", ItemType.ETC, "FAST"));
        itemRepository.save(new Item("testB", 20000, 20,
                false, "BUSAN", ItemType.BOOK, "NORMAL"));
    }

    @GetMapping
    public String items(Model model) {

        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "article/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {

    Item item = itemRepository.findById(itemId);
    model.addAttribute("item", item);

    return "article/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {

        model.addAttribute("item", new Item());

        return "article/addForm";
    }

    //RequestParam 을 사용한 POST (값은 HTML 에 name 으로 받아옴)
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

        return "article/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item) {
        /*
         * @ModelAttribute("item") Item item 은 아래와 같은 코드이다.
         * model.addAttribute("item", item); // 자동 추가, 생략이 가능하다.
         *
         * @ModelAttribute 의 이름을 생략할수 있다.
         * @ModelAttribute 의 이름을 생략하면 모델에 저장될 때 클래스명을 사용한다. 이때 클래스 첫글자만 소문자로 변경해서 등록 함.
         */
        itemRepository.save(item);

        return "article/item";
    }

    // @ModelAttribute 자체도 생략가능하다. 대상 객체는 모델에 자동 등록된다.
//    @PostMapping("/add")
    public String addItemV3(Item item) {

        itemRepository.save(item);

        return "article/item";
    }

    @PostMapping("/add")
    public String addItemV4(Item item, RedirectAttributes redirectAttributes) {

        log.info("item.open={}", item.getOpen());
        log.info("item.regions={}", item.getRegions());
        log.info("item.itemType={}", item.getItemType());

        // PRG Post/Redirect/Get 설정
        Item savedItem = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "article/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {

        itemRepository.update(itemId, item);

        return "redirect:/items/{itemId}";
    }


}