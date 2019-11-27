package com.sda.MyShoppingList.security;

import com.sda.MyShoppingList.entities.user.UserModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Setter
@Getter
public class MyUserDetails implements UserDetails {
    private String name;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;

    public MyUserDetails (UserModel userModel) {
        this.name = userModel.getName();
        this.password = userModel.getPassword();
        this.active = userModel.isActive();
        this.authorities = Arrays.stream(userModel.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public MyUserDetails() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
