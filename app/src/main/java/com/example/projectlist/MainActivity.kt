    package com.example.projectlist

    import android.os.Bundle
    import android.view.Menu
    import android.view.View
    import android.widget.AdapterView
    import android.widget.ArrayAdapter
    import android.widget.ListView
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity

    class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {
        lateinit var listView: ListView
        lateinit var adapter: ArrayAdapter<CharSequence>

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            listView = findViewById(R.id.listView)
            adapter = ArrayAdapter.createFromResource(
                this,
                R.array.countries,
                android.R.layout.simple_list_item_1
            )

            listView.adapter = adapter
            listView.setOnItemClickListener(this)
        }

        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            Toast.makeText(this, adapter.getItem(position), Toast.LENGTH_SHORT).show()
        }
    }
