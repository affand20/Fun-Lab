package id.trydev.funlab.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import id.trydev.funlab.ui.main.MainActivity
import id.trydev.funlab.R
import id.trydev.funlab.ui.register.RegisterActivity
import id.trydev.funlab.model.User
import id.trydev.funlab.preferences.AppPreferences
import id.trydev.funlab.utils.Hash
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var pref: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        pref = AppPreferences(this)

        firebaseFirestore = FirebaseFirestore.getInstance()

        tv_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        login_btn.setOnClickListener{
            if (validate()) {
                login(id_analis.text.toString(), password.text.toString())
            }
        }
    }

    private fun login(idAnalis:String, password:String) {
        progressBar2.visibility = View.VISIBLE
        firebaseFirestore.collection("users")
            .whereEqualTo("idAnalis", idAnalis)
            .whereEqualTo("password", Hash.sha512(password))
            .get()
            .addOnSuccessListener {
                progressBar2.visibility = View.GONE
                val listUser = mutableListOf<User>()
                for (user in it) {
                    listUser.add(user.toObject(User::class.java))
                }
                if (listUser.size>0) {
                    pref.idAnalis = listUser.get(0).idAnalis
                    pref.namaAnalis = listUser.get(0).namaAnalis
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Akun tidak ditemukan. Cek kembali ID dan password anda", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }

    private fun validate():Boolean {
        if (id_analis.text.toString().isEmpty()) {
            textInputLayout2.error = "ID Analis tidak boleh kosong"
            id_analis.requestFocus()
            return false
        }
        if (password.text.toString().isEmpty()) {
            textInputLayout3.error = "Password tidak boleh kosong"
            password.requestFocus()
            return false
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}