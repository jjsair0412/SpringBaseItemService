package hello.itemservice.domain.Item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data 얘는 핵심 비즈니스모델에 쓰기엔 위험하다.
@Getter
@Setter
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item(){
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
