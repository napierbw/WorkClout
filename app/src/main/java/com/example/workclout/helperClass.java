package com.example.workclout;

import android.support.v7.app.AppCompatDelegate;

public class helperClass
{
    public static String user_id;
    public static String login_type;
    public static boolean turn_on;
    public void set_user_id(String id)
    {
        user_id=id;
    }

    public String get_user_id()
    {
        return user_id;
    }

    public void set_login_type(String log)
    {
        login_type=log;
    }

    public String get_login_type()
    {
        return  login_type;
    }
    public void set_lights_on(boolean values)
    {
        turn_on=values;
    }
    public boolean get_lights_on()
    {
        return turn_on;
    }

}