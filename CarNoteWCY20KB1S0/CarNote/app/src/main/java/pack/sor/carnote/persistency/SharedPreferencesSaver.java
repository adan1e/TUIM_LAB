package pack.sor.carnote.persistency;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import pack.sor.carnote.model.AutoData;

/**
 * Klasa zapisująca stan aplikacji w pamięci wewnętrznej urządzenia
 */

public class SharedPreferencesSaver
{
    private static final String AUTO_PREF = "AUTO_PREF";

    public static void saveTo(ArrayList<AutoData> autoList, SharedPreferences preferences)
    {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        editor.putString(AUTO_PREF, gson.toJson(autoList));
        editor.apply();
    }

    public static ArrayList<AutoData> loadFrom(SharedPreferences preferences)
    {
        String string = preferences.getString(AUTO_PREF, null);
        Gson gson = new Gson();
        return gson.fromJson(string, new TypeToken<ArrayList<AutoData>>(){}.getType());
    }
}
