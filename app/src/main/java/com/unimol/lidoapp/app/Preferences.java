package com.unimol.lidoapp.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Preferences {
    private static final String PREFERENCE_NAME = "UserInfo";

    public static void savePreferences(Context context, String mail, String password) {
        SharedPreferences  sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mail", mail);
        editor.putString("password", password);
        editor.apply();
    }

    public static void saveMailPreferences(Context context, String mail) {
        SharedPreferences  sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mail", mail);
        editor.apply();
    }

    public static void saveOTPPreferences(Context context, String otp) {
        SharedPreferences  sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("otp", otp);
        editor.apply();
    }

    public static String getMailFromPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("mail"," ");
    }

    public static String getOTPFromPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("otp", " ");
    }

    public static void clearPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
