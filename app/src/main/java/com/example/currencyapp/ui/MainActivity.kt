package com.example.currencyapp.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.R
import com.example.currencyapp.adapters.CurrencyAdapter
import com.example.currencyapp.room.AppDatabase
import com.example.currencyapp.utils.Resource.Error
import com.example.currencyapp.utils.Resource.Success
import com.example.currencyapp.view_model.MainViewModel
import com.example.currencyapp.view_model.MainViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val adapter = CurrencyAdapter()
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val updateButton: Button = findViewById(R.id.update_currencies)
        val resultTextView: TextView = findViewById(R.id.edit_text_2)
        val pickCurButton: TextView = findViewById(R.id.currency_2)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(AppDatabase(this))
        )[MainViewModel::class.java]

        findViewById<RecyclerView>(R.id.recycler_view).also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this)
        }

        updateButton.setOnClickListener {
            viewModel.updateData()
            if (!viewModel.needAPiRequest) showSnackbar(viewModel.forecastLiveData.value?.message)
        }

        viewModel.forecastLiveData.observe(this, Observer {
            Log.d("observe triggered", "liveData observe triggered")
            when (it) {
                is Success -> {
                    adapter.setData(it.data!!)
                    if (viewModel.needAPiRequest) showSnackbar()
                }
                is Error -> {
                    if (!it.data.isNullOrEmpty()) adapter.setData(it.data)
                    if (viewModel.needAPiRequest) showSnackbar(it.message!!)
                }
            }
        })
        viewModel.converterResult.observe(this, Observer {
            resultTextView.text = it?.toString() ?: ""
        })
        viewModel.nowSelecting.observe(this, Observer {
            adapter.setSelectable(it)
        })

        if (viewModel.needAPiRequest) viewModel.updateData()

        findViewById<EditText>(R.id.edit_text_1).doOnTextChanged { text, _, _, _ ->
            val rate = viewModel.selectedCurrency?.Value ?: 1.0
            val amount = viewModel.selectedCurrency?.Nominal ?: 1
            val input = text.toString().toDoubleOrNull()

            if (input == null) viewModel.converterResult.value = null
            else viewModel.converterResult.value = input * rate / amount
        }
        pickCurButton.setOnClickListener {
            val isSelecting = viewModel.nowSelecting.value ?: true
            viewModel.nowSelecting.value = !isSelecting
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.needAPiRequest = false
    }

    private fun showSnackbar(message: String? = "Updated") {
        Snackbar.make(
            findViewById(R.id.coordinator),
            message ?: "Updated",
            Snackbar.LENGTH_SHORT
        ).show()
    }

}

