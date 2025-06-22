package ru.sad.tvframework.api

import android.net.Uri
import kotlinx.serialization.Serializable

@Serializable
data class ChannelModel(
    val name: String?,
    val number: String?,
    val tvInputId: String?,
    val id: String,
    val channelUri: String
)