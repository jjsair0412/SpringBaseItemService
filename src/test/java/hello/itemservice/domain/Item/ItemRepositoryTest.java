package hello.itemservice.domain.Item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void clear(){
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("주진성",10000,1);
        //when
        Item savedItem = itemRepository.save(item);
        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(savedItem).isEqualTo(findItem);

    }

    @Test
    void findAll() {
        //given
        Item item = new Item("주진성",10000,1);
        Item item2 = new Item("주진성주",13000,2);
        itemRepository.save(item);
        itemRepository.save(item2);
        //when
        List<Item> allItem = itemRepository.findAll();
        //then
        assertThat(allItem.size()).isEqualTo(2);
        assertThat(allItem).contains(item,item2);
    }

    @Test
    void updateItem() {
        //given
        Item item = new Item("주진성",10000,1);
        Item item1 = itemRepository.save(item);
        Long item1Id = item1.getId();
        //when
        Item updateParam= new Item("주진성2",20000,3);
        itemRepository.update(item1Id,updateParam);
        //then
        Item findItem = itemRepository.findById(item1Id);
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }
}