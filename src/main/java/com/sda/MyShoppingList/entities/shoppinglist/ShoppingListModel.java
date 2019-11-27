package com.sda.MyShoppingList.entities.shoppinglist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sda.MyShoppingList.abstractclasses.AbstractModel;
import com.sda.MyShoppingList.entities.product.ProductModel;
import com.sda.MyShoppingList.entities.user.UserModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Setter
@Getter
@Entity
public class ShoppingListModel extends AbstractModel<Long> {
    @Column(nullable = false, unique = false)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "shoppinglist_products",
            joinColumns = @JoinColumn(name = "shoppinglistmodel_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "productmodel_id", referencedColumnName = "id")
    )
    private Set<ProductModel> products;

    @OneToOne(mappedBy = "shoppingListModel")
    @JsonIgnore
    private UserModel userModel;

}
