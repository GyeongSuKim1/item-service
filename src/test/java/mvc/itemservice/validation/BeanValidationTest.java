package mvc.itemservice.validation;

import mvc.itemservice.domain.item.Item;
import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.util.Set;

public class BeanValidationTest {

    @Test
    void beanValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Item item = new Item();
        item.setItemName("  "); // 공백
        item.setPrice(0);
        item.setQuantity(10000);
        item.setOpen(true);
        item.setRegions(null);
        item.setItemType(null);
        item.setDeliveryCode(null);

        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        for (ConstraintViolation<Item> violation : violations) {
            System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
            System.out.println("violation = " + violation);
            System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
            System.out.println("violation.getMessage() = " + violation.getMessage());
        }
    }
}
