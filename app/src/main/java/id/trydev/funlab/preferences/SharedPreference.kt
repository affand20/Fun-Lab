package id.trydev.funlab.preferences

import android.content.Context
import android.content.SharedPreferences

class SharedPreference (context: Context) {

    private val PREFS_FILENAME = "id.trydev.funlab.prefs"
    private val prefs:SharedPreferences = context.getSharedPreferences(PREFS_FILENAME,Context.MODE_PRIVATE)

    private val ID_ANALIS = "ID_ANALIS"
    private val NAMA_ANALIS = "NAMA_ANALIS"

    var idAnalis: String
        get() = prefs.getString(ID_ANALIS, null)
        set(value) = prefs.edit().putString(ID_ANALIS, value).apply()

    var namaAnalis: String
        get() = prefs.getString(NAMA_ANALIS, null)
        set(value) = prefs.edit().putString(NAMA_ANALIS, value).apply()
}