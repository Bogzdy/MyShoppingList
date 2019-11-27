package com.sda.MyShoppingList.entities.user;

import com.sda.MyShoppingList.abstractclasses.AbstractModel;
import com.sda.MyShoppingList.entities.shoppinglist.ShoppingListModel;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
public class UserModel extends AbstractModel<Long> {
    @Column
    private String password;
    @Column
    private boolean active;
    @Column
    private String roles;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private ShoppingListModel shoppingListModel;
}
