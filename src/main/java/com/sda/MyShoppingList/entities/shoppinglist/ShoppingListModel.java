package com.sda.MyShoppingList.entities.shoppinglist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sda.MyShoppingList.abstractclasses.AbstractModel;
import com.sda.MyShoppingList.entities.order.Order;
import com.sda.MyShoppingList.entities.user.UserModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
public class ShoppingListModel extends AbstractModel<Long> {
    @Column(nullable = false, unique = false)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "shoppinglist_orders",
            joinColumns = @JoinColumn(name = "shoppinglistmodel_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id")
    )
    private Set<Order> orders;

    @OneToOne(mappedBy = "shoppingListModel")
    @JsonIgnore
    private UserModel userModel;

}
