package com.asantherrera.savemoney365days.ui.total

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asantherrera.savemoney365days.R
import com.asantherrera.savemoney365days.database.Saving
import java.text.SimpleDateFormat
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*


class TotalAdapter(var items: List<Saving>) : RecyclerView.Adapter<TotalAdapter.TotalViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TotalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_total, parent, false)
        return TotalViewHolder(view)
    }

    override fun onBindViewHolder(holder: TotalViewHolder, position: Int) {

        holder.bind(items[position])

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class TotalViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val amount = itemView.findViewById<TextView>(R.id.text_amount)
        private val date = itemView.findViewById<TextView>(R.id.text_date)

        fun bind(total: Saving) {
            val pattern = "dd MMM yyyy HH:mm"
            val formatter = SimpleDateFormat(pattern, Locale.US)
            formatter.timeZone = TimeZone.getTimeZone("GMT-6:00")
            val formatDate: String = formatter.format(total.date)
            val amountFormat = "$ ${total.id}"
            amount.text = amountFormat
            date.text = formatDate
        }

    }

}
