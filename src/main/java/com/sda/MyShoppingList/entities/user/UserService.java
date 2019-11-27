package com.sda.MyShoppingList.entities.user;

import com.sda.MyShoppingList.abstractclasses.AbstractService;
import com.sda.MyShoppingList.entities.product.ProductModel;
import com.sda.MyShoppingList.entities.product.ProductRepository;
import com.sda.MyShoppingList.entities.shoppinglist.ShoppingListModel;
import com.sda.MyShoppingList.entities.shoppinglist.ShoppingListRepository;
import com.sda.MyShoppingList.exception.BusinessExeption;
import com.sda.MyShoppingList.exception.Errors;
import com.sda.MyShoppingList.security.IAuthenticationFacade;
import com.sda.MyShoppingList.security.MyUserDetails;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Optional;

@Service
public class UserService extends AbstractService<Long, UserModel, UserRepository> {

    private ShoppingListRepository shoppingListRepository;
    private ProductRepository productRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    public UserService(UserRepository repository, ShoppingListRepository shoppingListRepository, ProductRepository productRepository) {
        super(repository);
        this.shoppingListRepository = shoppingListRepository;
        this.productRepository = productRepository;
}

    //Create or update user's shopping list
    public UserModel createOrUpdateShoppingList(ShoppingListModel shoppingListModel, Long userModelId) throws BusinessExeption {
        //Check if the new shopping list has a name
        if (shoppingListModel.getName() == null) throw new BusinessExeption(Errors.SHOPPING_LIST_NAME_MISSING);
        try{
            //check if current user has authority
           UserModel authorizedUser = authenticationFacade.hasAuthority(userModelId);
           //if user has no shopping list, create one
           if (authorizedUser.getShoppingListModel() == null) {
               ShoppingListModel savedShoppingList = shoppingListRepository.saveAndFlush(shoppingListModel);
               authorizedUser.setShoppingListModel(savedShoppingList);
               return repository.saveAndFlush(authorizedUser);
           // else update the existing shopping list
           } else {
               ShoppingListModel existingShoppingList = authorizedUser.getShoppingListModel();
               existingShoppingList.setName(shoppingListModel.getName());
               authorizedUser.setShoppingListModel(existingShoppingList);
               return repository.saveAndFlush(authorizedUser);
           }
       } catch (BusinessExeption e){
            throw new BusinessExeption(e);
        }

    }

    //Add a product for a specific user
    public ProductModel addProductToShoppingList(@NotNull Long productId, @NotNull Long userId) throws BusinessExeption {

        try{
            UserModel authorizedUser = authenticationFacade.hasAuthority(userId);
            //check if there is a shopping list
            if (authorizedUser.getShoppingListModel() == null) throw new BusinessExeption(Errors.SHOPPING_LIST_NOT_FOUND);

            //get product by id and check if is there
            Optional<ProductModel> existingProductModel = productRepository.findById(productId);
            existingProductModel.orElseThrow(() -> new BusinessExeption(Errors.PRODUCT_NOT_FOUND));

            //add product to the user shopping list
            authorizedUser.getShoppingListModel().getProducts().add(existingProductModel.get());
            //add the shoppingList_id to the product
            existingProductModel.get().getShoppingLists().add(authorizedUser.getShoppingListModel());

            return productRepository.saveAndFlush(existingProductModel.get());
        } catch (BusinessExeption e){
            throw new BusinessExeption(e);
        }
    }

    //Delete a product from shoppinglist
    public void deleteProductFromShoppingList(@NotNull Long productId, @NotNull Long userId) throws BusinessExeption {
        try {
            UserModel authorizedUser = authenticationFacade.hasAuthority(userId);
            //find shopping list
            Long shoppingListId = authorizedUser.getShoppingListModel().getId();
            Optional<ShoppingListModel> shoppingListModelToEdit = shoppingListRepository.findById(shoppingListId);
            shoppingListModelToEdit.orElseThrow(() -> new BusinessExeption(Errors.SHOPPING_LIST_NOT_FOUND));
            //find product
            Optional<ProductModel> productModelToDelete = productRepository.findById(productId);
            productModelToDelete.orElseThrow(() -> new BusinessExeption(Errors.PRODUCT_NOT_FOUND));
            //delete product from shopping list
            shoppingListModelToEdit.get().getProducts().remove(productModelToDelete.get());
            shoppingListRepository.saveAndFlush(shoppingListModelToEdit.get());
        } catch (BusinessExeption e){
            throw new BusinessExeption(e);
        }
    }

}
