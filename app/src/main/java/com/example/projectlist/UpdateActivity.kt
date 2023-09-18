package com.example.projectlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import com.example.projectlist.database.AppDatabase
import com.example.projectlist.database.entity.Mahasiswa

class UpdateActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val editTextNomorMahasiswa = findViewById<EditText>(R.id.editTextNomorMahasiswa)
        val editTextNama = findViewById<EditText>(R.id.editTextNama)
        val editTextTanggalLahir = findViewById<EditText>(R.id.editTextTanggalLahir)
        val radioGroupJenisKelamin = findViewById<RadioGroup>(R.id.radioGroupJenisKelamin)
        val radioButtonLaki = findViewById<RadioButton>(R.id.radioButtonLaki)
        val radioButtonPerempuan = findViewById<RadioButton>(R.id.radioButtonPerempuan)
        val editTextAlamat = findViewById<EditText>(R.id.editTextAlamat)
        val buttonUpdate = findViewById<Button>(R.id.buttonUpdate)

        database = AppDatabase.getInstance(applicationContext)

        val id = intent.getIntExtra("id", 0)
        val mhs = database.mhsdao().get(id)

        if (mhs != null) {
            editTextNomorMahasiswa.setText(mhs.uid)
            editTextNama.setText(mhs.namaLengkap)
            editTextTanggalLahir.setText(mhs.tanggalLahir)
            if (mhs.jenisKelamin == "Laki-laki") {
                radioButtonLaki.isChecked = true
            } else if (mhs.jenisKelamin == "Perempuan") {
                radioButtonPerempuan.isChecked = true
            }
            editTextAlamat.setText(mhs.alamat)
        } else {
            // Tampilkan pesan kesalahan jika data mahasiswa tidak ditemukan
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Error")
            alertDialogBuilder.setMessage("Data tidak ditemukan")
            alertDialogBuilder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        buttonUpdate.setOnClickListener {
            val nomorMahasiswa = editTextNomorMahasiswa.text.toString()
            val nama = editTextNama.text.toString()
            val tanggalLahir = editTextTanggalLahir.text.toString()
            val alamat = editTextAlamat.text.toString()
            val jenisKelamin = if (radioGroupJenisKelamin.checkedRadioButtonId == radioButtonLaki.id) {
                "Laki-laki"
            } else {
                "Perempuan"
            }

            if (nomorMahasiswa.isNotEmpty() && nama.isNotEmpty() && tanggalLahir.isNotEmpty() && alamat.isNotEmpty()) {
                val updatedMhs = Mahasiswa(
                    nomorMahasiswa,
                    nama,
                    tanggalLahir,
                    jenisKelamin,
                    alamat
                )
                database.mhsdao().update(updatedMhs)

                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("Sukses")
                alertDialogBuilder.setMessage("Data berhasil diupdate")
                alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
                    val intent = Intent(this,RecycleActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()


            } else {
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("Error")
                alertDialogBuilder.setMessage("Terdapat data yang belum diisi")
                alertDialogBuilder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        }
    }
}