package com.example.currencyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.R
import com.example.currencyapp.adapters.CurrencyAdapter
import com.example.currencyapp.room.AppDatabase
import com.example.currencyapp.utils.Resource.*
import com.example.currencyapp.view_model.MainViewModel
import com.example.currencyapp.view_model.MainViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val adapter = CurrencyAdapter()
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(AppDatabase(this))
        )[MainViewModel::class.java]

        val updateButton: Button = findViewById(R.id.update_currencies)
        findViewById<RecyclerView>(R.id.recycler_view).also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this)
        }

        updateButton.setOnClickListener {
            viewModel.updateData()
            if (!viewModel.needAPiRequest) showSnackbar()
        }

        viewModel.forecastLiveData.observe(this, Observer {
            Log.d("observe triggered", "liveData observe triggered")
            when (it) {
                is Success -> {
                    adapter.setData(it.data!!.Valute.currencies)
                    if (viewModel.needAPiRequest) showSnackbar()
                }
                is Error -> showSnackbar(it.message!!)
            }
        })

        if (viewModel.needAPiRequest) viewModel.updateData()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.needAPiRequest = false
    }

    private fun showSnackbar(message: String = "Updated") {
        Snackbar.make(
            findViewById(R.id.coordinator),
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

}

