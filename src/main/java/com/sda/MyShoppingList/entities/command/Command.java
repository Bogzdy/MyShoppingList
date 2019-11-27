package com.sda.MyShoppingList.entities.command;


import com.sda.MyShoppingList.entities.product.ProductModel;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Command {

    @Id
    private Long id;
    private ProductModel productModel;

    private int quantity;
}
