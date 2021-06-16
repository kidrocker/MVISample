package co.ke.paypay.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.ke.paypay.R
import co.ke.paypay.models.Quote
import co.ke.paypay.utils.GlobalUtils

class ConversionListAdapter(private val context: Context?) :
    RecyclerView.Adapter<ConversionListAdapter.ViewClassHolder>() {

    private var quotes: List<Quote> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewClassHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.currency_list_item, parent, false)
        )

    override fun onBindViewHolder(holder: ViewClassHolder, position: Int) {

        val item = quotes[position]

        holder.titleText.text = codeToName(item.code)
        holder.currencyCode.text = item.code.substring(3)
        holder.exchangeAmount.text = item.rateValue.toString()
    }

    // defines the size of the
    override fun getItemCount() = quotes.size


    // inner class to hold the view components
    inner class ViewClassHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleText: TextView = view.findViewById(R.id.title_text)
        var currencyCode: TextView = view.findViewById(R.id.currency_code_text)
        val exchangeAmount: TextView = view.findViewById(R.id.exchange_amount_text)
    }

    /**
     * Returns the currency name from code
     *
     */
    private fun codeToName(code: String): String {
        val currencies = GlobalUtils.stringToCurrencyList(context)
        return currencies.first {
            it.code == code.substring(3)
        }.name
    }

    /**
     * updates the list items when data changes
     * and notify the recyclerview
     */
    fun updateListItems(mQuotes: List<Quote>) {
        this.quotes = mQuotes
        notifyDataSetChanged()
    }
}