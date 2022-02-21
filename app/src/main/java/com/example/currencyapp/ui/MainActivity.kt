package com.example.currencyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.R
import com.example.currencyapp.adapters.CurrencyAdapter
import com.example.currencyapp.view_model.MainViewModel

class MainActivity : AppCompatActivity() {

    private val adapter = CurrencyAdapter()
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val updateButton : Button = findViewById(R.id.update_currencies)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view).also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this)
        }

        viewModel = MainViewModel()

        updateButton.setOnClickListener {
            viewModel.updateData()
        }

        viewModel.forecastLiveData.observe(this, Observer {
            adapter.setData(it.currencies)
        })
    }
}