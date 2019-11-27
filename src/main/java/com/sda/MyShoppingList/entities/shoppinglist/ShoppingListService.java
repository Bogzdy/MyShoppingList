package com.sda.MyShoppingList.entities.shoppinglist;

import com.sda.MyShoppingList.abstractclasses.AbstractService;
import com.sda.MyShoppingList.entities.command.Order;
import com.sda.MyShoppingList.entities.command.OrderRepository;
import com.sda.MyShoppingList.entities.product.ProductModel;
import com.sda.MyShoppingList.entities.product.ProductRepository;
import com.sda.MyShoppingList.exception.BusinessExeption;
import com.sda.MyShoppingList.exception.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ShoppingListService extends AbstractService<Long, ShoppingListModel, ShoppingListRepository> {

    @Autowired
    public ShoppingListService(ShoppingListRepository repository) {
        super(repository);
    }

    @Override
    public void delete(Long id) throws EmptyResultDataAccessException {
        Optional<ShoppingListModel> shoppingListToDelete = repository.findById(id);
        if (shoppingListToDelete.isPresent()) {
            shoppingListToDelete.get().getOrders().clear();
        }
        super.delete(id);
    }
}
