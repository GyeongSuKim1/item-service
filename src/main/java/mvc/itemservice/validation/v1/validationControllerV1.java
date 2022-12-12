//package mvc.itemservice.validation.v1;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import mvc.itemservice.domain.Repository.ItemRepository;
//import mvc.itemservice.domain.item.DeliveryCode;
//import mvc.itemservice.domain.item.Item;
//import mvc.itemservice.domain.item.ItemType;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.*;
//
//@Slf4j
//@Controller
//@RequestMapping("/v1/items")
//@RequiredArgsConstructor
//public class validationControllerV1 {
//
//    private final ItemRepository itemRepository;
//
//    // 등록지역
//    @ModelAttribute("regions")
//    public Map<String, String> regions() {
//
//        Map<String, String> regions = new LinkedHashMap<>(); // 해시맵은 순서가 보장이 안되서 링크드해시맵 사용
//
//        regions.put("SEOUL", "서울");
//        regions.put("BUSAN", "부산");
//        regions.put("JEJU", "제주");
//
//        return regions;
//    }
//
//    // 상품 종류
//    @ModelAttribute("itemTypes")
//    public ItemType[] itemTypes() {
//
//        return ItemType.values();
//    }
//
//    // 배송 방식
//    @ModelAttribute("deliveryCodes")
//    public List<DeliveryCode> deliveryCodes() {
//
//        List<DeliveryCode> deliveryCodes = new ArrayList<>();
//        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
//        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
//        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
//
//        return deliveryCodes;
//    }
//
//    @GetMapping
//    public String items(Model model) {
//
//        List<Item> items = itemRepository.findAll();
//        model.addAttribute("items", items);
//
//        return "validation/v1/items";
//    }
//
//    @GetMapping("/{itemId}")
//    public String item(@PathVariable Long itemId, Model model) {
//
//        Item item = itemRepository.findById(itemId);
//        model.addAttribute("item", item);
//
//        return "validation/v1/item";
//    }
//
//    @GetMapping("/add")
//    public String addForm(Model model) {
//
//        model.addAttribute("item", new Item());
//
//        return "validation/v1/addForm";
//    }
//
//    @PostMapping("/add")
//    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes,
//                            Model model) {
//
//        log.info("item.itemName={}", item.getItemName());
//        log.info("item.Price={}", item.getPrice());
//        log.info("item.Quantity={}", item.getQuantity());
//        System.out.println();
//        log.info("item.open={}", item.getOpen());
//        log.info("item.regions={}", item.getRegions());
//        log.info("item.itemType={}", item.getItemType());
//
//        // 검증 오류 결과를 보관
//        Map<String, String> errors = new HashMap<>();
//
//        // 검증 로직
//        if (!StringUtils.hasText(item.getItemName())) {
//            errors.put("itemName", "상품 이름은 필수입니다.");
//            System.out.println(errors);
//        }
//        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            errors.put("price", "가격은 1,000원 ~ 1,000,000원 까지 허용합니다.");
//            System.out.println(errors);
//        }
//        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
//            errors.put("quantity", "수량은 최대 9.999개 까지 허용합니다.");
//            System.out.println(errors);
//        }
//        if(item.getRegions().isEmpty()) {
//            errors.put("regions", "등록지역을 선택해주세요.");
//            System.out.println(errors);
//        }
//        if(item.getItemType() == null) {
//            errors.put("itemType", "상품 종류를 선택해주세요.");
//            System.out.println(errors);
//        }
//
//        // 특정 필드가 아닌 복합 룰 검증
//        if (item.getPrice() != null && item.getQuantity() != null) {
//            int resultPrice = item.getPrice() * item.getQuantity();
//            if (resultPrice < 10000) {
//                errors.put("globalError",
//                        "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice + "원");
//                System.out.println(errors);
//            }
//        }
//
//        // 검증에 실패하면 다시 입력 폼으로
//        if (!errors.isEmpty()) {
//            model.addAttribute("errors", errors);
//            return "validation/v1/addForm";
//        }
//
//        // 성공 로직
//        Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//
//        return "redirect:/v1/items/{itemId}";
//    }
//
//    @GetMapping("/{itemId}/edit")
//    public String editForm(@PathVariable Long itemId, Model model) {
//
//        Item item = itemRepository.findById(itemId);
//        model.addAttribute("item", item);
//
//        return "validation/v1/editForm";
//    }
//
//    @PostMapping("/{itemId}/edit")
//    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
//
//        itemRepository.update(itemId, item);
//
//        return "redirect:/v1/items/{itemId}";
//    }
//}