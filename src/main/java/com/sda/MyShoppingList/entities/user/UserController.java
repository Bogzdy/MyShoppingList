package com.sda.MyShoppingList.entities.user;

import com.sda.MyShoppingList.abstractclasses.AbstractController;
import com.sda.MyShoppingList.entities.order.Order;
import com.sda.MyShoppingList.entities.shoppinglist.ShoppingListModel;
import com.sda.MyShoppingList.exception.BusinessExeption;
import com.sda.MyShoppingList.security.IAuthenticationFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000/", "http://localhost:8080/"})
@RequestMapping("api/users")
public class UserController extends AbstractController<Long, UserModel, UserRepository, UserService> {
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    public UserController(UserService service) {
        super(service);
    }

    @PostMapping(path = "/{userId}/addShoppingList", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserModel> addShoppingList(@RequestBody ShoppingListModel shoppingListModel, @PathVariable(name = "userId") Long userId) {
        try {
            UserModel user = service.createOrUpdateShoppingList(shoppingListModel, userId);
            return ResponseEntity.ok(user);
        } catch (BusinessExeption e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/{userId}/{productModelId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Order> addOrderToShoppingList(
            @PathVariable(name = "productModelId") Long productModelId,
            @PathVariable(name = "userId") Long userId) {
        try {
            Order order = service.addOrderToShoppingList(productModelId, userId);
            return ResponseEntity.ok(order);
        } catch (BusinessExeption e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(path = "/{userId}/{orderId}")
    public ResponseEntity deleteOrderFromShoppingList(@PathVariable Long orderId, @PathVariable Long userId) {
        try {
            service.deleteOrderFromShoppingList(orderId, userId);
            return ResponseEntity.noContent().build();
        } catch (BusinessExeption e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PostMapping(consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserModel> add(@RequestBody UserModel model) throws BusinessExeption {
        return super.add(model);
    }

    @Override
    @GetMapping(produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserModel>> get() {
        return super.get();
    }

    @Override
    @GetMapping(path = "/{id}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserModel> get(@PathVariable Long id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(path = "/{id}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id) throws EmptyResultDataAccessException {
        return super.delete(id);
    }

    @Override
    @PutMapping(consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserModel> update(@RequestBody UserModel model) {
        return super.update(model);
    }
}
