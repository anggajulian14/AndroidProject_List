package com.example.projectlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectlist.adapter.Adapter
import com.example.projectlist.database.AppDatabase
import com.example.projectlist.database.entity.Mahasiswa
import com.example.projectlist.databinding.ActivityRecycleBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RecycleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecycleBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private var list = mutableListOf<Mahasiswa>()
    private lateinit var adapter: Adapter
    private lateinit var database: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecycleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        fab = binding.fab

        database = AppDatabase.getInstance(applicationContext)
        adapter = Adapter(list)
        adapter.setDialog(object : Adapter.Dialog{

            override fun onClick(position: Int) {
                val dialogOptions = AlertDialog.Builder(this@RecycleActivity)
                dialogOptions.setTitle(list[position].namaLengkap)
                dialogOptions.setItems(R.array.item_option) { dialog, which ->
                    when (which) {
                        0 -> {
                            // Tampilkan data tanpa opsi edit
                            val intent = Intent(this@RecycleActivity, DetailActivity::class.java)
                            val id = list[position].uid.toIntOrNull() ?: 0
                            intent.putExtra("id", id)
                            Log.d("RecycleActivity", "Intent ID: $id")
                            startActivity(intent)
                        }
                        1 -> {
                            // Edit data
                            val intent = Intent(this@RecycleActivity, UpdateActivity::class.java)
                            val id = list[position].uid.toIntOrNull() ?: 0
                            intent.putExtra("id", id)
                            startActivity(intent)
                        }
                        2 -> {
                            // Hapus data
                            database.mhsdao().delete(list[position])
                            getData()
                        }
                    }
                    dialog.dismiss()
                }
                val dialogView = dialogOptions.create()
                dialogView.show()
            }
        })

        recyclerView.adapter = adapter // Inisialisasi adapter

        recyclerView.layoutManager = LinearLayoutManager(applicationContext,
            RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext,
            RecyclerView.VERTICAL
        ))

        fab.setOnClickListener {
            intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        getData() // Panggil getData() saat activity di-resume
    }

    private fun getData() {
        list.clear()
        list.addAll(database.mhsdao().getAll())
        adapter.notifyDataSetChanged()
    }
}