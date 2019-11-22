package com.sda.MyShoppingList.exception;

import lombok.Getter;

@Getter
public enum Errors {
    SHOPPING_LIST_NOT_FOUND("E001", "The requested shopping list has not been found!"),
    PRODUCT_NOT_FOUND("E002", "The requested product has not been found!"),
    INVALID_FIELD_VALUE("E003", "Invalid field value");
    String code;
    String message;

    Errors(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
