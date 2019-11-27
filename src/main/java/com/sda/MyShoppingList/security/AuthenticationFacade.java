package com.sda.MyShoppingList.security;

import com.sda.MyShoppingList.entities.user.UserModel;
import com.sda.MyShoppingList.entities.user.UserRepository;
import com.sda.MyShoppingList.exception.BusinessExeption;
import com.sda.MyShoppingList.exception.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    private UserRepository userRepository;

    public AuthenticationFacade(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    //Check if the authenticated user is the one who make the calls
    public UserModel hasAuthority(Long userId) throws BusinessExeption {
        Authentication authentication = getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        //get user by id and check if is there
        Optional<UserModel> foundUser = userRepository.findById(userId);
        foundUser.orElseThrow(() -> new BusinessExeption(Errors.USER_NOT_FOUND));
        //check if the foundUser is the same with principal
        if (!myUserDetails.getName().equals(foundUser.get().getName())) throw new BusinessExeption(Errors.ACCESS_DENIED);

        return foundUser.get();
    }
}
