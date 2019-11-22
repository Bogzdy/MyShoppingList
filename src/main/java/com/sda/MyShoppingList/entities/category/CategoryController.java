package com.sda.MyShoppingList.entities.category;

import com.sda.MyShoppingList.abstractclasses.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController extends AbstractController<Long, CategoryModel, CategoryRepository, CategoryService> {
    @Autowired
    public CategoryController(CategoryService service) {
        super(service);
    }
}
