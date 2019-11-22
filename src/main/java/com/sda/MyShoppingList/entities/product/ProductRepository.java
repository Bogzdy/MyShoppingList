package com.sda.MyShoppingList.entities.product;

import com.sda.MyShoppingList.utils.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    List<ProductModel> findByName(String name);

    List<ProductModel> findFirst5ByNameContainingOrDetailsContaining(String name, String details);
}
