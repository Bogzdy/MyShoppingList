package com.sda.MyShoppingList.entities.category;

import com.sda.MyShoppingList.abstractclasses.AbstractModel;
import com.sda.MyShoppingList.entities.product.ProductModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Setter
@Getter
@Entity
public class CategoryModel extends AbstractModel<Long> {

    @OneToMany(mappedBy = "category")
    private List<ProductModel> products;

}
