package com.huriati.project.tvkabel;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefProfil {
    public static final String SP_APP = "spApp";

    public static final String id="",
            userid="",
            identity_number="",
            registration_number="",
            phone="",
            name="",
            date_of_birth="",
            place_of_birth="",
            sex="",
            religion="",
            bloodTypeText="",
            maritalStatusText="",
            sexText="",
            domicileText="",
            addressText="",
            weight="",
            height="",
            nationality = "";
    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefProfil(Context context){
        sp = context.getSharedPreferences(SP_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public SharedPrefProfil() {
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

    public String getId() {
        return sp.getString(id,"");
    }

    public String getUserid() {
        return sp.getString(userid,"");
    }

    public String getIdentity_number() {
        return sp.getString(identity_number,"");
    }

    public String getRegistration_number() {
        return sp.getString(registration_number,"");
    }

    public String getPhone() {
        return sp.getString(phone,"");
    }
    public String getName() {
        return sp.getString(name,"");
    }

    public String getDate_of_birth() {
        return sp.getString(date_of_birth,"");
    }

    public String getPlace_of_birth() {
        return sp.getString(place_of_birth,"");
    }

    public String getSex() {
        return sp.getString(sex,"");
    }

    public String getReligion() {
        return sp.getString(religion,"");
    }

    public String getBloodTypeText() {
        return sp.getString(bloodTypeText,"");
    }

    public String getMaritalStatusText() {
        return sp.getString(maritalStatusText,"");
    }

    public String getSexText() {
        return sp.getString(sexText,"");
    }

    public String getDomicileText() {
        return sp.getString(domicileText,"");
    }

    public String getAddressText() {
        return sp.getString(addressText,"");
    }

    public String getWeight() {
        return sp.getString(weight,"");
    }

    public String getHeight() {
        return sp.getString(height,"");
    }

    public String getNationality() {
        return sp.getString(nationality,"");
    }
}

