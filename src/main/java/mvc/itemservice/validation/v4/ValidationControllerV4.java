package mvc.itemservice.validation.v4;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mvc.itemservice.domain.Repository.ItemRepository;
import mvc.itemservice.domain.item.*;
import mvc.itemservice.domain.item.dto.ItemSaveDto;
import mvc.itemservice.domain.item.dto.ItemUpdateDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/v4/items")
@RequiredArgsConstructor
public class ValidationControllerV4 {

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

    @GetMapping
    public String items(Model model) {

        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "validation/v4/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "validation/v4/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {

        model.addAttribute("item", new Item());

        return "validation/v4/addForm";
    }

    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute("item") ItemSaveDto saveDto,
                          BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        log.info("item.itemName={}", saveDto.getItemName());
        log.info("item.Price={}", saveDto.getPrice());
        log.info("item.Quantity={}", saveDto.getQuantity());
        System.out.println();
        log.info("item.open={}", saveDto.getOpen());
        log.info("item.regions={}", saveDto.getRegions());
        log.info("item.itemType={}", saveDto.getItemType());

        // 오브젝트 오류
        if (saveDto.getPrice() != null && saveDto.getQuantity() != null) {
            int resultPrice = saveDto.getPrice() * saveDto.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v4/addForm";
        }

        // 성공 로직
        Item item = new Item();
        item.setItemName(saveDto.getItemName());
        item.setPrice(saveDto.getPrice());
        item.setQuantity(saveDto.getQuantity());
        item.setOpen(saveDto.getOpen());
        item.setRegions(saveDto.getRegions());
        item.setItemType(saveDto.getItemType());
        item.setDeliveryCode(saveDto.getDeliveryCode());

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/v4/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "validation/v4/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute("item") ItemUpdateDto updateDto,
                       BindingResult bindingResult) {

        if (updateDto.getPrice() != null && updateDto.getQuantity() != null) {
            int resultPrice = updateDto.getPrice() * updateDto.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v4/editForm";
        }

        Item item = new Item();
        item.setId(updateDto.getId());
        item.setItemName(updateDto.getItemName());
        item.setPrice(updateDto.getPrice());
        item.setQuantity(updateDto.getQuantity());
        item.setOpen(updateDto.getOpen());
        item.setRegions(updateDto.getRegions());
        item.setItemType(updateDto.getItemType());
        item.setDeliveryCode(updateDto.getDeliveryCode());

        itemRepository.update(itemId, item);

        return "redirect:/v4/items/{itemId}";
    }
}