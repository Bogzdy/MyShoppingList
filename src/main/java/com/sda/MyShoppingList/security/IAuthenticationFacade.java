package com.sda.MyShoppingList.security;

import com.sda.MyShoppingList.entities.user.UserModel;
import com.sda.MyShoppingList.exception.BusinessExeption;
import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade  {
    Authentication getAuthentication();
    UserModel hasAuthority(Long userId) throws BusinessExeption;
}
