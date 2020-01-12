package id.trydev.funlab.ui.main.tentang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import id.trydev.funlab.R
import id.trydev.funlab.ui.login.LoginActivity
import id.trydev.funlab.preferences.AppPreferences
import id.trydev.funlab.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_tentang.*

class TentangActivity : AppCompatActivity() {

    private lateinit var preference:AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tentang)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Tentang"

        preference = AppPreferences(this)

        logout.setOnItemClickListener { adapterView, view, i, l ->
            Log.d("LOGOUT", "${logout.getItemAtPosition(i)}")
            if (logout.getItemAtPosition(i)=="Keluar") {
                preference.resetPreference()
                startActivity(Intent(this, LoginActivity::class.java))
                MainActivity().finish()
                this.finish()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId==android.R.id.home) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
