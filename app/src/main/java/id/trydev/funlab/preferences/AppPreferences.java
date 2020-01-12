package id.trydev.funlab.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    public static final String PREFS_NAME = "app_pref";

    private static final String ID_ANALIS = "id_analis";
    private static final String NAMA_ANALIS = "nama_analis";

    private final SharedPreferences prefs;

    public AppPreferences(Context context){
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setNamaAnalis(String token){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(NAMA_ANALIS, token);
        editor.apply();
    }

    public String getNamaAnalis(){
        return prefs.getString(NAMA_ANALIS, null);
    }

    public void setIdAnalis(String userId){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ID_ANALIS, userId);
        editor.apply();
    }

    public String getIdAnalis(){
        return prefs.getString(ID_ANALIS, null);
    }

    public void resetPreference(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }


}
