package com.sda.MyShoppingList.entities.shoppinglist;

import com.sda.MyShoppingList.abstractclasses.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
