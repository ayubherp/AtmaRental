package com.ayubherpracoyo.atmarental.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.ayubherpracoyo.atmarental.model.User;

public class UserPreferences {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public static final String IS_LOGIN = "isLogin";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_TOKEN = "access_token";
    public static final String KEY_ROLE = "role";
    public static final String KEY_ID_ROLE = "id_role";
    public static final String KEY_FULL_NAME = "full_name";

    public UserPreferences(Context context) {
        this.context = context;
        /* penamaan bebas namun disini digunakan "userPreferences" */
        sharedPreferences = context.getSharedPreferences("userPreferences",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setUser(Long id,String name, String email, String password, String access_token, String role, String id_role, String full_name){

        /* Menyimpan data login ke sharedPreferences dengan key dan value  */
        editor.putBoolean(IS_LOGIN, true);
        editor.putLong(KEY_ID,id);
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PASSWORD,password);
        editor.putString(KEY_TOKEN,access_token);
        editor.putString(KEY_ROLE, role);
        editor.putString(KEY_ID_ROLE,id_role);
        editor.putString(KEY_FULL_NAME,full_name);
        /* Jangan lupa commit karena kalo hanya set editonya saja tidak commit akan sia-sia */
        editor.commit();
    }

    public User getUserLogin(){
        /* Mengembalikan object User untuk menampilkan data user jika user sudah login */
        String name,email,access_token, role, id_role, full_name;
        long id;

        id = sharedPreferences.getLong(KEY_ID,0);
        name = sharedPreferences.getString(KEY_NAME,null);
        email = sharedPreferences.getString(KEY_EMAIL,null);
        access_token = sharedPreferences.getString(KEY_TOKEN, null);
        role = sharedPreferences.getString(KEY_ROLE, null);
        id_role = sharedPreferences.getString(KEY_ID_ROLE, null);
        full_name = sharedPreferences.getString(KEY_FULL_NAME, null);

        return new User(id,name,email,
                "","","","",access_token, role, id_role, full_name);
    }

    public boolean checkLogin(){
        /* Mengembalikan nilai is_login, jika sudah login otomatis nilai true jika tidak akan return false */
        return sharedPreferences.getBoolean(IS_LOGIN,false);
    }

    public void logout(){
        /* Melakukan clear data yang ada pada sharedPreferences  , jangan lupa di commit agar data terubah*/
        editor.clear();
        editor.commit();
    }
}
