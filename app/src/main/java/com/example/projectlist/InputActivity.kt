package com.example.projectlist

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import com.example.projectlist.database.AppDatabase
import com.example.projectlist.database.entity.Mahasiswa
import com.example.projectlist.databinding.ActivityInputBinding
import java.util.Calendar

class InputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputBinding

    private lateinit var database: AppDatabase
    private var selectedYear = 0
    private var selectedMonth = 0
    private var selectedDay = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val NomorMahasiswa = binding.editTextNomorMahasiswa
        val Nama = binding.editTextNama
        val TanggalLahir = binding.editTextTanggalLahir
        val JenisKelamin = binding.radioGroupJenisKelamin
        val Laki = binding.radioButtonLaki
        val Perempuan = binding.radioButtonPerempuan
        val Alamat = binding.editTextAlamat
        val buttonSimpan = binding.buttonSimpan

        database = AppDatabase.getInstance(applicationContext)

        val intent = intent.extras
        if (intent != null) {
            val id = intent.getInt("id")
            val mhs = database.mhsdao().get(id)

            NomorMahasiswa.setText(mhs.uid)
            Nama.setText(mhs.namaLengkap)
            TanggalLahir.setText(mhs.tanggalLahir)

            // Set jenis kelamin
            if (mhs.jenisKelamin == "Laki-laki") {
                Laki.isChecked = true
            } else if (mhs.jenisKelamin == "Perempuan") {
                Perempuan.isChecked = true
            }
            Alamat.setText(mhs.alamat)
        }

        TanggalLahir.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    // Update selected date
                    selectedYear = year
                    selectedMonth = monthOfYear
                    selectedDay = dayOfMonth

                    // Update EditText with selected date
                    TanggalLahir.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
                },
                year,
                month,
                day
            )

            // Show DatePickerDialog
            datePickerDialog.show()
        }


        buttonSimpan.setOnClickListener {
            val nomorMahasiswa = NomorMahasiswa.text.toString()
            val nama = Nama.text.toString()
            val tanggalLahir = TanggalLahir.text.toString()
            val alamat = Alamat.text.toString()

            if (nama.isNotEmpty() && alamat.isNotEmpty() && nomorMahasiswa.isNotEmpty() && tanggalLahir.isNotEmpty()) {
                val selectedRadioButtonId = JenisKelamin.checkedRadioButtonId
                if (selectedRadioButtonId != -1) {
                    val jenisKelamin = if (selectedRadioButtonId == Laki.id) {
                        "Laki-laki"
                    } else {
                        "Perempuan"
                    }

                    // Cek apakah nomor mahasiswa sudah ada sebelumnya
                    val existingMahasiswa = database.mhsdao().getByNomorMahasiswa(nomorMahasiswa)
                    if (existingMahasiswa != null) {
                        val alertDialogBuilder = AlertDialog.Builder(this)
                        alertDialogBuilder.setTitle("Error")
                        alertDialogBuilder.setMessage("Nomor Mahasiswa sudah ada")
                        alertDialogBuilder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                        val alertDialog = alertDialogBuilder.create()
                        alertDialog.show()
                    } else {
                        // Jika nomor mahasiswa belum ada, lakukan penambahan data
                        database.mhsdao().insertAll(
                            Mahasiswa(
                                nomorMahasiswa,
                                nama,
                                tanggalLahir,
                                jenisKelamin,
                                alamat
                            )
                        )

                        val alertDialogBuilder = AlertDialog.Builder(this)
                        alertDialogBuilder.setTitle("Berhasil")
                        alertDialogBuilder.setMessage("Data berhasil ditambahkan")
                        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
                            val intent = Intent(this, RecycleActivity::class.java)
                            startActivity(intent)
                            finish()}
                        val alertDialog = alertDialogBuilder.create()
                        alertDialog.show()


                    }
                } else {
                    val alertDialogBuilder = AlertDialog.Builder(this)
                    alertDialogBuilder.setTitle("Error")
                    alertDialogBuilder.setMessage("Jenis kelamin harus diisi")
                    alertDialogBuilder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }
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