package id.trydev.funlab.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import id.trydev.funlab.ui.main.MainActivity
import id.trydev.funlab.R
import id.trydev.funlab.ui.login.LoginActivity
import id.trydev.funlab.model.User
import id.trydev.funlab.preferences.AppPreferences
import id.trydev.funlab.utils.Hash
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var firebaseFirestore:FirebaseFirestore
    private lateinit var pref:AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        pref = AppPreferences(this)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

        tv_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        register_btn.setOnClickListener {
            if (validate()) {
                register(id_analis.text.toString(), nama_analis.text.toString(), password.text.toString())
            }
        }

    }

    private fun register(idAnalis:String, namaAnalis:String, password:String) {
        progressBar.visibility = View.VISIBLE
        val user = User(idAnalis, namaAnalis, Hash.sha512(password))
        val listUser = mutableListOf<User>()
        firebaseFirestore.collection("users")
            .whereEqualTo("idAnalis", idAnalis)
            .get()
            .addOnSuccessListener {
                for (users in it) {
                    listUser.add(users.toObject(User::class.java))
                }
                Log.d("LIST_USER_LENGTH", "${listUser.size}")
                val isUnique = listUser.size>0
                if (!isUnique) {
                    firebaseFirestore.collection("users")
                        .document(idAnalis)
                        .set(user)
                        .addOnSuccessListener {
                            pref.idAnalis = idAnalis
                            pref.namaAnalis = namaAnalis
                            progressBar.visibility = View.GONE
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener {
                            progressBar.visibility = View.GONE
                            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                } else {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, "Akun sudah terdaftar", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Akun sudah terdaftar", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun validate():Boolean {
        if (id_analis.text.toString().isEmpty()) {
            textInputLayout2.error = "ID analis tidak boleh kosong"
            return false
        }
        if (nama_analis.text.toString().isEmpty()) {
            textInputLayout5.error = "Nama analis tidak boleh kosong"
            return false
        }
        if (password.text.toString().isEmpty()) {
            textInputLayout3.error = "Password tidak boleh kosong"
            return false
        }
        if (confirm_password.toString().isEmpty()) {
            textInputLayout.error = "Konfirmasi password tidak boleh kosong"
            return false
        }
        if (!confirm_password.text.toString().equals(password.text.toString())) {
            textInputLayout.error = "Password tidak cocok"
            return false
        }
        return true
    }
}