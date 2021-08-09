package hello.itemservice.web.basic;

import hello.itemservice.domain.Item.Item;
import hello.itemservice.domain.Item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;


    /**
     * lombok의 RequiredArgsConstructor를 이용해서 주입하는 코드를 생략해줄 수 있다.
    @Autowired
    public BasicItemController(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }
    **/

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute(item);
        return "basic/item";
    }

    /**
     * 같은 url을 Http 메서드로만 구분했다.
     */
    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    /**
    @PostMapping("/add")
    public String saveV2(@RequestParam String itemName,
                       @RequestParam int price,
                        @RequestParam Integer quantity,
                       Model model){
        Item item = new Item(itemName,price,quantity);
        itemRepository.save(item);
        model.addAttribute("item",item);
        return "basic/item";
    }

     @ModelAttribute("item") 이렇게 해주면 - name 속성을 넣어주면
     Item에 값들을 set해주고 item의 이름을 갖게 할 수 있다.
     또한 자동으로 model에 addAttribute까지 해준다..!!!!

     model.addAttribute랑 item.set--- 두개를 해줌. 모델의 name은 "item"
     */
//    @PostMapping("/add")
    public String saveV1(@ModelAttribute("item") Item item, Model model){
        itemRepository.save(item);
//        model.addAttribute("item",item);
        /**
         * 모델에 값을 넘겨주는 이유는 view를 새로만들지 않고, 저장된 결과물을 바로 보여주기위해서 넣었다.
         */
        return "basic/item";
    }

//    @PostMapping("/add")
    public String saveV2(Item item){
        itemRepository.save(item);
        return "basic/item";
    }

    /**
     * PRG패턴을 적용한 saveV3
     * 저장을 완료한 후 반환할 view 이름에 저장한 item의 id를 넣어줌으로써
     * 상품 상세화면으로 넘겨버린다.
     *
     * 그러나 id를 넣어줬을때 uri에는 한글이나 이런게 들어가면 안됀다.
     * id값이 그대로 노출된다는 약점도 있다
     * 따라서 이렇게하면 안돼고
     * redirectAttribute라는것을 사용해야 한다.
     */
    @PostMapping("/add")
    public String saveV3(Item item){
        itemRepository.save(item);
        return "redirect:/basic/items/"+item.getId();
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item",findItem);
        return"basic/editForm";
    }

    /**
     상품 수정을 진행하면 리다이렉트로 받아온 id의 정보로 돌아가게끔 구성
     */
//    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, Item item){
        Item item1 = new Item(item.getItemName(),item.getPrice(),item.getQuantity());
        itemRepository.update(itemId,item1);
        return "redirect:/basic/items/{itemId}";
    }

    @PostMapping("/{itemId}/edit")
    public String editV2(@PathVariable Long itemId,@ModelAttribute Item item){
        itemRepository.update(itemId,item);
        return "redirect:/basic/items/{itemId}";
    }


    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,20));
    }

}
