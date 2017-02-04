package org.nullpointer.client;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by tanishka on 4/2/17.
 */

public class LoginPreferences {
    private static final String MAIL="MAIL";
    private static  final  String NAME="NAME";
    public  static String getStoredMail(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(MAIL,null);
    }

    public static void setMail(Context context,String mail){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(MAIL,mail).apply();
    }
    public  static String getStoredName(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(NAME,null);
    }

    public static void setName(Context context,String name){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(NAME,name).apply();
    }
}
