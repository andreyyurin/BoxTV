package ru.sad.boxtv.ui

import kotlinx.serialization.Serializable
import ru.sad.tvframework.api.ChannelModel

@Serializable
data object TvScreen

@Serializable
data class TvViewScreen(val channelModel: ChannelModel)