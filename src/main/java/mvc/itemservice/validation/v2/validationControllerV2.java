package mvc.itemservice.validation.v2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mvc.itemservice.domain.Repository.ItemRepository;
import mvc.itemservice.domain.item.DeliveryCode;
import mvc.itemservice.domain.item.Item;
import mvc.itemservice.domain.item.ItemType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/v2/items")
@RequiredArgsConstructor
public class validationControllerV2 {

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

        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {

        model.addAttribute("item", new Item());

        return "validation/v2/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1 (@ModelAttribute Item item, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        // 검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError(
                    "item", "itemName", "상품 이름은 필수입니다."));
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError(
                    "item", "price", "가격은 1,000원 ~ 1,000,000원 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError(
                    "item", "quantity", "수량은 최대 9.999개 까지 허용합니다."));
        }
        if (item.getRegions().isEmpty()) {
            bindingResult.addError(new FieldError(
                    "item", "regions", "등록지역을 선택해주세요."));
        }
        if (item.getItemType() == null) {
            bindingResult.addError(new FieldError(
                    "item", "itemType", "상품 종류를 선택해주세요."));
        }

        // 특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError(
                        "globalError",
                        "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice + "원"));
            }
        }

        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v2/addForm";
        }

        // 성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/v2/items/{itemId}";
    }

    @PostMapping("/add")
    public String addItemV2 (@ModelAttribute Item item, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        // 검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(),
                    false, null, null, "상품 이름은 필수입니다."));
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", item.getPrice(),
                    false, null, null, "가격은 1,000원 ~ 1,000,000원 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(),
                    false, null, null, "수량은 최대 9.999개 까지 허용합니다."));
        }
        if (item.getRegions().isEmpty()) {
            bindingResult.addError(new FieldError("item", "regions", item.getRegions(),
                    false, null , null, "등록지역을 선택해주세요."));
        }
        if (item.getItemType() == null) {
            bindingResult.addError(new FieldError("item", "itemType", item.getItemType(),
                    false, null, null, "상품 종류를 선택해주세요."));
        }

        // 특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError(
                        "globalError",
                        null,
                        null,
                        "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice + "원"));
            }
        }

        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v2/addForm";
        }

        // 성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/v2/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {

        itemRepository.update(itemId, item);

        return "redirect:/v2/items/{itemId}";
    }


}