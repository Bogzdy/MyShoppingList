package com.sda.MyShoppingList.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic()
//                .and().formLogin()
//                .loginPage("/index.html")
//                .loginProcessingUrl("/process_login")
//                .defaultSuccessUrl("/test")
//                .failureUrl("/").permitAll()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/api/users/**").hasAnyAuthority("ADMIN", "USER")
//                .antMatchers("/api/category").hasAuthority("ADMIN")
//                .antMatchers("/api/shoppingList").hasAnyAuthority("ADMIN", "USER")
//                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated()
//                .and().cors()
//                .and().csrf().disable().authorizeRequests();

        http
//                .and().formLogin()
//                .loginPage("http://localhost:3000/login")
//                .loginProcessingUrl("/process_login")
//                .defaultSuccessUrl("/test")
//                .failureUrl("http://localhost:3000/login").permitAll()
                .authorizeRequests()


                .antMatchers("/api/users/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/api/category").hasAuthority("ADMIN")
                .antMatchers("/api/shoppingList").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/**").permitAll().anyRequest().authenticated()

                .and().cors()
                .and().csrf().disable()
                .httpBasic().and().formLogin()
                .loginPage("http://localhost:3000/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("http://localhost:3000/test");

    }


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
