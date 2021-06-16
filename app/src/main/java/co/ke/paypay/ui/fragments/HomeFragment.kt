package co.ke.paypay.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.ke.paypay.R
import co.ke.paypay.adapters.ConversionListAdapter
import co.ke.paypay.adapters.DropdownAdapter
import co.ke.paypay.models.Conversion
import co.ke.paypay.models.Currency
import co.ke.paypay.state.DataState
import co.ke.paypay.ui.viewmodels.ConversionStateEvent
import co.ke.paypay.ui.viewmodels.ConversionViewModel
import co.ke.paypay.utils.GlobalUtils
import com.google.android.material.textfield.TextInputLayout

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val conversionViewModel: ConversionViewModel by hiltNavGraphViewModels(R.id.navigation_graph)
    private lateinit var currency: Currency
    private lateinit var loader: ProgressBar
    private lateinit var adapter: ConversionListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subScribeObservers()
        adapter = ConversionListAdapter(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialize items
        val convertButton = view.findViewById<Button>(R.id.convert_button)
        val currencyDropdown = view.findViewById<TextInputLayout>(R.id.from_text).editText as AutoCompleteTextView
        val currencies = GlobalUtils.stringToCurrencyList(context)
        val currencyAdapter = context?.let { DropdownAdapter(it, currencies) }
        loader = view.findViewById(R.id.progressBar)
        val convertedList = view.findViewById<RecyclerView>(R.id.converted_list)
        val conversionAmount = view.findViewById<TextInputLayout>(R.id.currency_amount_text)

        // add data to dropdown
        currencyDropdown.setAdapter(currencyAdapter)
        currencyDropdown.threshold = 3
        currencyDropdown.setOnItemClickListener { parent, _, position, _ ->
            currency = parent.adapter.getItem(position) as Currency
            currencyDropdown.setText(currency.name)
        }


        convertButton.setOnClickListener {
            val amount = conversionAmount.editText?.text.toString()
            if (this::currency.isInitialized && amount.isNotBlank()) {
                conversionViewModel.setStateEvent(ConversionStateEvent.Convert(currency.code, amount.toDouble()))
            } else
                Toast.makeText(context, "Make sure you have selected currency and entered the amount", Toast.LENGTH_SHORT).show()
        }

        // set up list
        convertedList.layoutManager = LinearLayoutManager(context)
        convertedList.adapter = adapter
    }

    private fun subScribeObservers() {
        conversionViewModel.dataState.observe(this, { dataState ->

            when (dataState) {
                is DataState.Success<Conversion> -> {
                    loader.visibility = View.GONE

                    // set the data to the recyclerview
                    adapter.updateListItems(dataState.data.quote)
                }
                is DataState.Error -> {
                    dataState.exception.message?.let { Log.d("ERROR", it) }

                    loader.visibility = View.GONE
                    Toast.makeText(context, dataState.exception.message, Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {

                    // data is loading show this to the user
                    loader.visibility = View.VISIBLE
                }
            }
        })
    }

}