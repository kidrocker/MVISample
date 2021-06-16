package co.ke.paypay.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import co.ke.paypay.R
import co.ke.paypay.models.Currency

class DropdownAdapter(ctx: Context, private val cry:List<Currency>): ArrayAdapter<Currency>(ctx , 0,cry) {

    private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {

        val currency = cry[position]

        val view = recycledView ?: LayoutInflater.from(context).inflate(
                R.layout.spinner_item,
                parent,
                false
        )

       val name = view.findViewById<TextView>(R.id.currency_title_text)
       val code = view.findViewById<TextView>(R.id.currency_code_text)

        name.text = currency.name
        code.text = currency.code

        return view
    }

    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

}