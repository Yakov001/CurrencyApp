package com.example.currencyapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.R
import com.example.currencyapp.model.Currency

class CurrencyAdapter(private var dataSet: List<Currency>? = null, private val listener: OnCurrencySelectedListener) :
    RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    interface OnCurrencySelectedListener {
        fun onCurrencySelected(currency: Currency)
    }

    private var nowSelecting = false

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val currencyAbbrevTextView: TextView = view.findViewById(R.id.currency_abbrev)
        val currencyTextView: TextView = view.findViewById(R.id.currency_text)
        val currencyAmount: TextView = view.findViewById(R.id.currency_amount)
        val currencyRate: TextView = view.findViewById(R.id.currency_rate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View? = null
        when (viewType) {
            0 -> view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_item, parent, false)
            1 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_view_item_shadow, parent, false)
                view.isClickable = true
            }
        }
        return ViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (dataSet != null) {
            holder.currencyAbbrevTextView.text = dataSet!![position].abbreviation
            holder.currencyTextView.text = dataSet!![position].Name
            "${dataSet!![position].Nominal}  ".also { holder.currencyAmount.text = it }
            holder.currencyRate.text = String.format("%.2f", dataSet!![position].Value)

            if (nowSelecting) holder.itemView.setOnClickListener {
                listener.onCurrencySelected(dataSet!![position])
            }
        }
    }

    override fun getItemCount(): Int {
        return dataSet?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (nowSelecting) 1 else 0
    }

    fun setData(dataSet: List<Currency>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    fun setSelectable(selectable: Boolean = false) {
        nowSelecting = selectable
        notifyDataSetChanged()
    }
}