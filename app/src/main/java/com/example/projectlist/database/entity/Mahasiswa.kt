package com.example.projectlist.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Mahasiswa (
    @PrimaryKey(autoGenerate = false)
    @NonNull
    var uid : String,
    @ColumnInfo(name = "nama_lengkap")
    var namaLengkap : String?,
    @ColumnInfo(name = "tanggal_lahir")
    var tanggalLahir : String?,
    @ColumnInfo(name = "jenis_kelamin")
    var jenisKelamin : String?,
    @ColumnInfo(name = "alamat")
    var alamat : String?
)
