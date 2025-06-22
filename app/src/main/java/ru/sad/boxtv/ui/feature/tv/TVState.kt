package ru.sad.boxtv.ui.feature.tv

import ru.sad.tvframework.api.ChannelModel

sealed class TVState {

    object Loading : TVState()

    data class Success(val channels: List<ChannelModel>) : TVState()

    data class Error(val error: String) : TVState()
}