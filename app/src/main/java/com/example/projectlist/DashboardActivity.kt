package com.example.projectlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.projectlist.databinding.ActivityDashboardBinding
import com.example.projectlist.databinding.ActivityMainBinding
import kotlin.collections.List

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menghubungkan spinner pada layout
        val spinner = binding.spinner

        // Membuat data yang akan ditampilkan pada spinner
        val data: List<String> = listOf("Pilih salah satu", "List Negara", "Data Mahasiswa")

        // Membuat adapter untuk spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)

        // Menentukan tampilan dropdown item-itemnya
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Menghubungkan spinner ke adapter
        spinner.adapter = adapter

        // Membuat index 0 pada variabel data menjadi default
        spinner.setSelection(0)

        // Tambahkan listener untuk mendeteksi pemilihan item
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Memeriksa pemilihan item
                if (position == 1) {
                    // Jika "List Negara" dipilih, buat intent ke MainActivity
                    val intent = Intent(this@DashboardActivity, MainActivity::class.java)
                    startActivity(intent)
                } else if (position == 2) {
                    // Jika "Data Mahasiswa" dipilih, buat intent ke RecycleActivity
                    val intent = Intent(this@DashboardActivity, RecycleActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Metode ini akan dipanggil jika tidak ada yang dipilih.
            }
        }
    }
}
