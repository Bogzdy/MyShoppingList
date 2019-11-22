package com.sda.MyShoppingList.entities.shoppinglist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingListRepository extends JpaRepository<ShoppingListModel, Long> {


}
