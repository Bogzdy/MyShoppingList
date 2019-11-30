package com.sda.MyShoppingList.entities.user;

import com.sda.MyShoppingList.abstractclasses.AbstractService;
import com.sda.MyShoppingList.entities.order.Order;
import com.sda.MyShoppingList.entities.order.OrderRepository;
import com.sda.MyShoppingList.entities.product.ProductModel;
import com.sda.MyShoppingList.entities.product.ProductRepository;
import com.sda.MyShoppingList.entities.shoppinglist.ShoppingListModel;
import com.sda.MyShoppingList.entities.shoppinglist.ShoppingListRepository;
import com.sda.MyShoppingList.exception.BusinessExeption;
import com.sda.MyShoppingList.exception.Errors;
import com.sda.MyShoppingList.security.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class UserService extends AbstractService<Long, UserModel, UserRepository> {

    private ShoppingListRepository shoppingListRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    public UserService(
            UserRepository repository,
            ShoppingListRepository shoppingListRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository) {
        super(repository);
        this.shoppingListRepository = shoppingListRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
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
    public Order addOrderToShoppingList(@NotNull Long productId, @NotNull Long userId) throws BusinessExeption {

        try{
            UserModel authorizedUser = authenticationFacade.hasAuthority(userId);
            //check if there is a shopping list
            if (authorizedUser.getShoppingListModel() == null) throw new BusinessExeption(Errors.SHOPPING_LIST_NOT_FOUND);

            //get product by id and check if is there
            Optional<ProductModel> existingProductModel = productRepository.findById(productId);
            existingProductModel.orElseThrow(() -> new BusinessExeption(Errors.PRODUCT_NOT_FOUND));

            //Convert Product to an Order
            Order myOrder = mapOrderFromProduct(existingProductModel.get());

            //add order to the user shopping list
            authorizedUser.getShoppingListModel().getOrders().add(myOrder);
            //add the shoppingList_id to the product
            myOrder.getShoppingLists().add(authorizedUser.getShoppingListModel());

            return orderRepository.saveAndFlush(myOrder);
        } catch (BusinessExeption e){
            throw new BusinessExeption(e);
        }
    }

    //Delete a product from shoppinglist
    public void deleteOrderFromShoppingList(@NotNull Long orderId, @NotNull Long userId) throws BusinessExeption {
        try {
            UserModel authorizedUser = authenticationFacade.hasAuthority(userId);
            //find shopping list
            Long shoppingListId = authorizedUser.getShoppingListModel().getId();
            Optional<ShoppingListModel> shoppingListModelToEdit = shoppingListRepository.findById(shoppingListId);
            shoppingListModelToEdit.orElseThrow(() -> new BusinessExeption(Errors.SHOPPING_LIST_NOT_FOUND));
            //find order
            Optional<Order> foundOrder = authorizedUser
                    .getShoppingListModel()
                    .getOrders().stream()
                    .filter(order -> order.getId().equals(orderId))
                    .findFirst();
            foundOrder.orElseThrow(() -> new BusinessExeption(Errors.ORDER_NOT_FOUND));
            System.out.println(foundOrder.get());
            //delete order from shopping list
            shoppingListModelToEdit.get().getOrders().remove(foundOrder.get());
            shoppingListRepository.saveAndFlush(shoppingListModelToEdit.get());
        } catch (BusinessExeption e){
            throw new BusinessExeption(e);
        }
    }

    private Order mapOrderFromProduct(ProductModel productModel) {
        Order newOrder = new Order();
        newOrder.setName(productModel.getName());
        newOrder.setMeasurementUnits(productModel.getMeasurementUnits());
        return newOrder;
    }

}
