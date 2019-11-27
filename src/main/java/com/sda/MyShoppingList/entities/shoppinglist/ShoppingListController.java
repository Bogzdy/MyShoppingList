package com.sda.MyShoppingList.entities.shoppinglist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sda.MyShoppingList.abstractclasses.AbstractController;
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

    @PostMapping(path = "/{listId}/{productId}", produces = "application/json")
    public ResponseEntity<ProductModel> addProductToShoppingList(@PathVariable(name = "productId") Long productModelId, @PathVariable(name = "listId") Long shoppingListModelId) {
        try {
            ProductModel productModel = service.addProductToShoppingList(productModelId, shoppingListModelId);
            return ResponseEntity.ok(productModel);
        } catch (BusinessExeption e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Ex: /api/shoppingList/4?productIds=3&productIds=4 (4 = shoppingListId; 3, 4 = product id)
    @PutMapping(path = "/{shoppingListId}")
    public ResponseEntity<ShoppingListModel> updateShoppingList(
            @PathVariable Long shoppingListId,
            @RequestParam(value = "productIds") Long... productId) {
        try{
            ShoppingListModel shoppingListModel = service.updateShoppingList(shoppingListId, productId);
            return ResponseEntity.ok(shoppingListModel);
        } catch (BusinessExeption e){
            return ResponseEntity.notFound().build();
        }
    }
}
