package com.example.projectlist

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectlist.database.AppDatabase
import com.example.projectlist.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var binding: ActivityDetailBinding  // Menggunakan View Binding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflasi layout menggunakan View Binding
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        database = AppDatabase.getInstance(applicationContext)

        // Mengakses elemen-elemen layout menggunakan View Binding
        val textViewNomorMahasiswa = binding.textViewNomorMahasiswa
        val textViewNama = binding.textViewNama
        val textViewTanggalLahir = binding.textViewTanggalLahir
        val textViewJenisKelamin = binding.textViewJenisKelamin
        val textViewAlamat = binding.textViewAlamat

        val intent = intent
        if (intent != null && intent.hasExtra("id")) {
            val id = intent.getIntExtra("id", 0)
            if (id != -1) {
                val mahasiswa = database.mhsdao().get(id)
                textViewNomorMahasiswa.text = "Nomor Mahasiswa: ${mahasiswa.uid}"
                textViewNama.text = "Nama: ${mahasiswa.namaLengkap}"
                textViewTanggalLahir.text = "Tanggal Lahir: ${mahasiswa.tanggalLahir}"
                textViewJenisKelamin.text = "Jenis Kelamin: ${mahasiswa.jenisKelamin}"
                textViewAlamat.text = "Alamat: ${mahasiswa.alamat}"
            }
        }
    }
}