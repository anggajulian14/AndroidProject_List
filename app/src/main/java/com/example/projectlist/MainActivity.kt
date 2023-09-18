package com.example.projectlist

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectlist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    lateinit var listView: ListView
    lateinit var adapter: ArrayAdapter<CharSequence>
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listView = binding.listView
        adapter = ArrayAdapter.createFromResource(
            this,
            R.array.countries,
            android.R.layout.simple_list_item_1
        )

        listView.adapter = adapter
        listView.setOnItemClickListener(this)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedCountry = adapter.getItem(position).toString()
        Toast.makeText(this, selectedCountry, Toast.LENGTH_SHORT).show()
    }
}
