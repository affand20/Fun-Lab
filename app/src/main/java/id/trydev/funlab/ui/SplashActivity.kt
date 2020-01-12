package id.trydev.funlab.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.firebase.FirebaseApp
import id.trydev.funlab.R
import id.trydev.funlab.ui.login.LoginActivity
import id.trydev.funlab.preferences.AppPreferences
import id.trydev.funlab.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    lateinit var prefs:AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        prefs = AppPreferences(this)
        prefs = AppPreferences(this)

        Log.d("SPLASH_ACTIVITY", "${prefs.idAnalis}, ${prefs.namaAnalis}")

        Handler().postDelayed({
            if (prefs.idAnalis.isNullOrEmpty() && prefs.namaAnalis.isNullOrEmpty()) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
            FirebaseApp.initializeApp(this)
        }, 1500)
    }
}
