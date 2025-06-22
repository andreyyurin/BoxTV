package ru.sad.boxtv.ui.feature.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.sad.tvframework.api.TifManager

class TVViewModel(
    private val tifManager: TifManager
) : ViewModel() {

    private val _channelsState = MutableStateFlow<TVState>(TVState.Loading)
    val channelsState = _channelsState.asStateFlow()

    init {
        initChannels()
    }

    fun initChannels() {
        viewModelScope.launch {
            runCatching { tifManager.getAllChannels() }
                .onSuccess { channels ->
                    _channelsState.emit(
                        TVState.Success(
                            channels
                                .filter { it.number != null }
                        )
                    )
                }
                .onFailure { _channelsState.emit(TVState.Error(it.localizedMessage.orEmpty())) }
        }

    }
}