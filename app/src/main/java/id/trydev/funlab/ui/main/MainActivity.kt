package id.trydev.funlab.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.trydev.funlab.R
import id.trydev.funlab.preferences.AppPreferences
import id.trydev.funlab.ui.main.riwayat.RiwayatActivity
import id.trydev.funlab.ui.main.scan.ScanActivity
import id.trydev.funlab.ui.main.stock.StockActivity
import id.trydev.funlab.ui.main.tentang.TentangActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var preferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences = AppPreferences(this)

        ib_scan_botol.setOnClickListener {
            startActivity(Intent(this, ScanActivity::class.java))
        }

        tv_scan_botol.setOnClickListener {
            startActivity(Intent(this, ScanActivity::class.java))
        }

        ib_riwayat.setOnClickListener {
            startActivity(Intent(this, RiwayatActivity::class.java))
        }

        tv_riwayat.setOnClickListener {
            startActivity(Intent(this, RiwayatActivity::class.java))
        }

        ib_tambah_stok.setOnClickListener {
            startActivity(Intent(this, StockActivity::class.java))
        }

        tv_tambah_stok.setOnClickListener {
            startActivity(Intent(this, StockActivity::class.java))
        }

        ib_tentang.setOnClickListener {
            startActivity(Intent(this, TentangActivity::class.java))
            finish()
        }

        tv_tentang.setOnClickListener {
            startActivity(Intent(this, TentangActivity::class.java))
            finish()
        }
    }
}
