package com.sda.MyShoppingList.entities.shoppinglist;

import com.sda.MyShoppingList.abstractclasses.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/shoppingList")
public class ShoppingListController extends AbstractController<Long, ShoppingListModel, ShoppingListRepository, ShoppingListService> {
    @Autowired
    public ShoppingListController(ShoppingListService service) {
        super(service);
    }

}
