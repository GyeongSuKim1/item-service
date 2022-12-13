package mvc.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.util.Collections;
import java.util.List;

@Data
public class ItemGroups {

    @NotNull(groups = UpdateCheck.class)    // 수정시에만 적용
    private Long id;

    @NotBlank(groups = {SaveCheck.class, UpdateCheck.class})
    private String itemName;

    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    @Range(min = 1000, max = 1000000,
            groups = {SaveCheck.class, UpdateCheck.class})
    private Integer price;

    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    @Max(value = 100000, groups = UpdateCheck.class)    // 수정시에만 허용
    @Max(value = 9999, groups = SaveCheck.class)    // 등록시에만 사용
    private Integer quantity;

    private Boolean open;   // 판매 여부

    @Size(min = 1, message = "등록 지역은 필수 입니다.",
            groups = {SaveCheck.class, UpdateCheck.class})
    private List<String> regions;   // 등록 지역

    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    private ItemType itemType;  // 상품 종류

    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    private String deliveryCode;    // 배송 방식

    public ItemGroups() { }

    public ItemGroups(String itemName, Integer price, Integer quantity,
                Boolean open, String regions, ItemType itemType, String deliveryCode) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.open = open;
        this.regions = Collections.singletonList(regions);
        this.itemType = itemType;
        this.deliveryCode = deliveryCode;
    }
}