package com.sda.MyShoppingList.exception;

import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLIntegrityConstraintViolationException;

public class BusinessExeption extends Exception {
    public BusinessExeption(Exception e) {

        if (e instanceof DataIntegrityViolationException){
            System.out.println("Dublicate field value in SQL Database.");
            e.printStackTrace();
        }else {
            e.printStackTrace();
        }
    }

    public BusinessExeption(Errors error) {
        super(error.getMessage());
    }

    public BusinessExeption(String message) {
        super(message);
        System.out.println(message);
    }


}
