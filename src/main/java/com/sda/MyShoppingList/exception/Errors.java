package com.sda.MyShoppingList.exception;

import lombok.Getter;

@Getter
public enum Errors {
    SHOPPING_LIST_NOT_FOUND("E001", "The requested shopping list has not been found!"),
    PRODUCT_NOT_FOUND("E002", "The requested product has not been found!"),
    INVALID_FIELD_VALUE("E003", "Invalid field value"),
    SHOPPING_LIST_ALREADY_THERE("E004", "Shopping list already found!"),
    USER_NOT_FOUND("E005", "User not found!"),
    SHOPPING_LIST_NAME_MISSING("E006", "Shopping list name is missing!"),
    ACCESS_DENIED("E007", "Access denied");

    String code;
    String message;

    Errors(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
