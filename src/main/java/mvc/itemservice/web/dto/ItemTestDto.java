package mvc.itemservice.web.dto;

import lombok.Data;
import mvc.itemservice.domain.item.ItemType;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ItemTestDto {

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotNull
    @Max(value = 9999)
    private Integer quantity;

    private Boolean open;   // 판매 여부

    @NotNull
    @Size(min = 1, message = "등록지역은 필수 입니다.")
    private List<String> regions;   // 등록 지역

    @NotNull
    private ItemType itemType;  // 상품 종류

    @NotNull
    private String deliveryCode;    // 배송 방식

//    public ItemTestDto(){
//
//    }
}
