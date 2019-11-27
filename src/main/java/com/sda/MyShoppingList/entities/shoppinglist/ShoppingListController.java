package com.sda.MyShoppingList.entities.shoppinglist;

import com.sda.MyShoppingList.abstractclasses.AbstractController;
import com.sda.MyShoppingList.entities.command.Order;
import com.sda.MyShoppingList.entities.product.ProductModel;
import com.sda.MyShoppingList.exception.BusinessExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/shoppingList")
public class ShoppingListController extends AbstractController<Long, ShoppingListModel, ShoppingListRepository, ShoppingListService> {
    @Autowired
    public ShoppingListController(ShoppingListService service) {
        super(service);
    }

}
