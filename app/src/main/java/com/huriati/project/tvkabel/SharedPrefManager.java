package com.huriati.project.tvkabel;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String SP_APP = "spApp";

    public static final String SP_ID = "spId";
    public static final String SP_IDTAGIHAN = "spIdKolektor";
    public static final String SP_EMAIL = "spEmail";
    public static final String SP_AUTH = "spAuth";
    public static final String SP_NAME = "spName";
    public static final String SP_WILAYAH = "spWilayah";
    public static final String SP_WILAYAHNAME = "spWilayahName";
    public static final String SP_ALAMAT = "spAlamat";
    public static final String SP_NOTELP = "spNotelp";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public SharedPrefManager() {
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPId(){
        return sp.getString(SP_ID, "");
    }
    public String getSpIdkolektor(){
        return sp.getString(SP_IDTAGIHAN, "");
    }

    public String getSPEmail(){
        return sp.getString(SP_EMAIL, "");
    }

    public String getAuth(){
        return sp.getString(SP_AUTH, "");
    }
    public String getName(){
        return sp.getString(SP_NAME, "");
    }
    public String getSpWilayah(){
        return sp.getString(SP_WILAYAH, "");
    }
    public String getSpWilayahNama(){
        return sp.getString(SP_WILAYAHNAME, "");
    }
    public String getSpNotelp(){
        return sp.getString(SP_NOTELP, "");
    }
    public String getSpAlamat(){
        return sp.getString(SP_ALAMAT, "");
    }

    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }
}

