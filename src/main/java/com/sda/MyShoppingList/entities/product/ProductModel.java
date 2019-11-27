package com.sda.MyShoppingList.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sda.MyShoppingList.abstractclasses.AbstractModel;
import com.sda.MyShoppingList.entities.category.CategoryModel;
import com.sda.MyShoppingList.entities.command.Order;
import com.sda.MyShoppingList.entities.shoppinglist.ShoppingListModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class ProductModel extends AbstractModel<Long> {
    @Column
    private Float price;

    @Column
    private String details;

    @Column
    private Float quantity;

    @Enumerated(EnumType.STRING)
    @Column
    private MeasurementUnits measurementUnits;

    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    @JsonIgnore
    private CategoryModel category;

//    @ManyToMany(mappedBy = "products")
//    @JsonIgnore
//    private Set<ShoppingListModel> shoppingLists = new HashSet<>();

    @OneToMany(mappedBy = "productModel", cascade = CascadeType.ALL)
    @JsonIgnore
    @Transient
    private List<Order> orders;

}
