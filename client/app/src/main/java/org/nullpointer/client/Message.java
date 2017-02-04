package org.nullpointer.client;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by tanishka on 4/2/17.
 */

public class Message {
    public static  void message(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}