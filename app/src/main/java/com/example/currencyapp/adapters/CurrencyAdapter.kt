package com.example.currencyapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.R
import com.example.currencyapp.model.Currency

class CurrencyAdapter(private var dataSet: List<Currency>? = null) :
    RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val currencyTextView : TextView = view.findViewById(R.id.currency_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (dataSet != null) {
           holder.currencyTextView.text = dataSet!![position].abbreviation
        }
    }

    override fun getItemCount(): Int {
        return dataSet?.size ?: 50
    }

    fun setData(dataSet: List<Currency>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }
}