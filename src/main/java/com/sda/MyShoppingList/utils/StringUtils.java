package com.sda.MyShoppingList.utils;

import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {


    public static boolean isAValidName(String string){
        return string.matches("[a-zA-Z0-9-: ]*");
    }

}
