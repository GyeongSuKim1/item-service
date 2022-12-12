package mvc.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;

@Data
public class Item {

    private Long id;

    @NotBlank
    private String itemName;

    @NotBlank
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotBlank
    @Max(9999)
    private Integer quantity;

    private Boolean open;   // 판매 여부

    @NotBlank
    private List<String> regions;   // 등록 지역

    @NotBlank
    private ItemType itemType;  // 상품 종류

    @NotBlank
    private String deliveryCode;    // 배송 방식

    public Item() { }

    public Item(String itemName, Integer price, Integer quantity,
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