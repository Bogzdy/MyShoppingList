package com.sda.MyShoppingList.entities.product;

import com.sda.MyShoppingList.abstractclasses.AbstractService;
import com.sda.MyShoppingList.entities.category.CategoryModel;
import com.sda.MyShoppingList.entities.category.CategoryRepository;
import com.sda.MyShoppingList.exception.BusinessExeption;
import com.sda.MyShoppingList.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService extends AbstractService<Long, ProductModel, ProductRepository> {

    private CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository repository, CategoryRepository categoryRepository) {
        super(repository);
        this.categoryRepository = categoryRepository;
    }


    public ProductModel add(ProductModel productModel, Long categoryId) throws BusinessExeption {
        CategoryModel categoryModel = categoryRepository.getOne(categoryId);
        productModel.setCategory(categoryModel);
        try {
            return super.add(productModel);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessExeption(e);
        }
    }

    public ProductModel updateProduct(ProductModel productModel, Long categoryId) throws BusinessExeption {
        Optional<CategoryModel> categoryModel = categoryRepository.findById(categoryId);
        if (productModel.getId() == null || !categoryModel.isPresent()) {
            throw new IllegalArgumentException();
        }
        Optional<ProductModel> existingProduct = repository.findById(productModel.getId());

        if (existingProduct.isPresent()) {
            productModel.setCategory(categoryModel.get());

            try {
                return super.add(productModel);
            } catch (DataIntegrityViolationException e) {
                throw new BusinessExeption(e);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public List<ProductModel> findFirstFiveByNameOrDetails(String keyWord) {
        return repository.findFirst5ByNameContainingOrDetailsContaining(keyWord, keyWord);
    }
}
