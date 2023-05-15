package com.unimol.lidoapp.app;

import java.util.regex.Pattern;

public class Constants {
    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    public static final Pattern MAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
    public static String NGROKURL = "ngrockURL"; //MAPPING YOUR LOCALHOST TO THE INTERNET
    public static String URLREST = NGROKURL + "/LidoWEBServer/rest/";
    public static int MAXPASSWORDLENGTH = 30;
    public static int MAXMAILLENGTH = 40;
    public static int MAXNAMELENGTH = 20;
    public static int MAXSURNAMELENGTH = 20;
    public static String MAILMANAGER = "mailUsedbyManager";

}
