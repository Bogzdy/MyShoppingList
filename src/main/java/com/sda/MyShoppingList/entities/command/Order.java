package com.sda.MyShoppingList.entities.command;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sda.MyShoppingList.abstractclasses.AbstractModel;
import com.sda.MyShoppingList.entities.product.MeasurementUnits;
import com.sda.MyShoppingList.entities.product.ProductModel;
import com.sda.MyShoppingList.entities.shoppinglist.ShoppingListModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "myOrder")
public class Order extends AbstractModel<Long> {

    @ManyToOne
    @JoinColumn(name = "productName")
    private ProductModel productModel;

    @Column
    private Float quantity;

    @Enumerated(EnumType.STRING)
    @Column
    private MeasurementUnits measurementUnits;

    @ManyToMany(mappedBy = "orders")
    @JsonIgnore
    private Set<ShoppingListModel> shoppingLists = new HashSet<>();
}
