package com.sda.MyShoppingList.security;

import com.sda.MyShoppingList.entities.user.UserModel;
import com.sda.MyShoppingList.entities.user.UserRepository;
import com.sda.MyShoppingList.exception.BusinessExeption;
import com.sda.MyShoppingList.exception.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    //Check if the authenticated user is the one who make the calls or has ADMIN authority
    public UserModel hasAuthority(Long userId) throws BusinessExeption {
        Authentication authentication = getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        //get user by id and check if is there
        Optional<UserModel> foundUser = userRepository.findById(userId);
        foundUser.orElseThrow(() -> new BusinessExeption(Errors.USER_NOT_FOUND));
        //check if the foundUser is the same with principal
        boolean hasAuthority = myUserDetails.getName().equals(foundUser.get().getName());
        boolean isAdmin = myUserDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        if (hasAuthority || isAdmin) {
            return foundUser.get();
        }
        throw new BusinessExeption(Errors.ACCESS_DENIED);
    }
}
