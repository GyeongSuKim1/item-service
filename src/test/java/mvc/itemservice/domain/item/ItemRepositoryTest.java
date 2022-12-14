package mvc.itemservice.domain.item;

import mvc.itemservice.domain.Repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {

        // given
        Item item = new Item("testA", 10000, 10,
                true, "SEOUL", ItemType.ETC, "FAST");

        // when
        Item savedItem = itemRepository.save(item);

        // then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void fondAll() {

        // given
        Item itemA = new Item("testA", 10000, 10,
                true, "SEOUL", ItemType.ETC, "FAST");
        Item itemB = new Item("testB", 12200, 18,
                false, "BUSAN", ItemType.BOOK, "SLOW");

        itemRepository.save(itemA);
        itemRepository.save(itemB);

        // whan
        List<Item> result = itemRepository.findAll();

        // then
        assertThat(result.size()).isEqualTo(2); // 객체 갯수
        assertThat(result).contains(itemA, itemB);  // 객체 찾기
    }

    @Test
    void updateItem() {

        // given
        Item item = new Item("testA", 10000, 10,
                true, "SEOUL", ItemType.ETC, "FAST");

        Item saveItem = itemRepository.save(item);
        Long itemId = saveItem.getId();

        // when
        Item updateParam = new Item("testA", 9900, 3,
                true, "SEOUL", ItemType.FOOD, "NORMAL");
        itemRepository.update(itemId, updateParam);

        //then
        Item findItem = itemRepository.findById(itemId);
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());    // ItemName 이 같은지
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());  // Price 가 같은지
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());    // Quantity 가 같은지

    }


}
