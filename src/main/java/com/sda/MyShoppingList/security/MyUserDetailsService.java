package com.sda.MyShoppingList.security;

import com.sda.MyShoppingList.entities.user.UserModel;
import com.sda.MyShoppingList.entities.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private IAuthenticationFacade authenticationFacade;


    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<UserModel> userModel = userRepository.findByName(name);
        if (!userModel.isPresent()) {
            throw new UsernameNotFoundException("Not found: " + name);
        }
        return userModel.map(MyUserDetails::new).get();
    }



}
