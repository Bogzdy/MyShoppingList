package com.sda.MyShoppingList.entities.shoppinglist;

import com.sda.MyShoppingList.abstractclasses.AbstractService;
import com.sda.MyShoppingList.entities.product.ProductModel;
import com.sda.MyShoppingList.entities.product.ProductRepository;
import com.sda.MyShoppingList.exception.BusinessExeption;
import com.sda.MyShoppingList.exception.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ShoppingListService extends AbstractService<Long, ShoppingListModel, ShoppingListRepository> {
    private ProductRepository productRepository;

    @Autowired
    public ShoppingListService(ShoppingListRepository repository, ProductRepository productRepository) {
        super(repository);
        this.productRepository = productRepository;
    }

    public ProductModel addProductToShoppingList(@NotNull Long productModelId, @NotNull Long shoppingListId) throws BusinessExeption {

        Optional<ProductModel> foundProductModel = productRepository.findById(productModelId);
        Optional<ShoppingListModel> foundShoppingList = repository.findById(shoppingListId);

        if (foundProductModel.isPresent() && foundShoppingList.isPresent()) {
            foundShoppingList.get().getProducts().add(foundProductModel.get());
            foundProductModel.get().getShoppingLists().add(foundShoppingList.get());

            return productRepository.saveAndFlush(foundProductModel.get());
        } else {
            throw new BusinessExeption(Errors.SHOPPING_LIST_NOT_FOUND);
        }
    }

    public ShoppingListModel updateShoppingList(@NotNull Long shoppingListId, @NotNull Long... productId) throws BusinessExeption {
        Optional<ShoppingListModel> foundShoppingList = repository.findById(shoppingListId);
        if (foundShoppingList.isPresent()) {
            ArrayList<Long> productIds = new ArrayList<>(Arrays.asList(productId));
            if (productIds.size() != 0){
                for (Long id : productId) {
                    Optional<ProductModel> foundProduct = productRepository.findById(id);
                    foundProduct.ifPresent(productModel -> foundShoppingList.get().getProducts().remove(productModel));
                }
            }
            return repository.saveAndFlush(foundShoppingList.get());
        } else {
            throw new BusinessExeption(Errors.PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public void delete(Long id) throws EmptyResultDataAccessException {
        Optional<ShoppingListModel> shoppingListToDelete = repository.findById(id);
        if (shoppingListToDelete.isPresent()) {
            shoppingListToDelete.get().getProducts().clear();
        }
        super.delete(id);
    }
}
