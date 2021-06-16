package co.ke.paypay.ui.viewmodels

import androidx.lifecycle.*
import co.ke.paypay.models.Conversion
import co.ke.paypay.repo.ConversionRepository
import co.ke.paypay.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversionViewModel @Inject
constructor(
    private val conversionRepository: ConversionRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _dataState: MutableLiveData<DataState<Conversion>> = MutableLiveData()
    val dataState: LiveData<DataState<Conversion>>
        get() = _dataState

    fun setStateEvent(stateEvent: ConversionStateEvent) {
        viewModelScope.launch {
            when (stateEvent) {
                is ConversionStateEvent.Convert -> {
                    conversionRepository.getConversions(stateEvent.code, stateEvent.amount)
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                is ConversionStateEvent.None -> {
                    // Do something here
                }
            }
        }
    }
}

sealed class ConversionStateEvent {
    data class Convert(val code:String, val amount:Double) : ConversionStateEvent()
    object None : ConversionStateEvent()
}