package com.sda.MyShoppingList.entities.category;

import com.sda.MyShoppingList.abstractclasses.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractService<Long, CategoryModel, CategoryRepository> {

    @Autowired
    public CategoryService(CategoryRepository repository) {
        super(repository);
    }

}
