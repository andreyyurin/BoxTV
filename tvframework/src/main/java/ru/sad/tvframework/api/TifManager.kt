package ru.sad.tvframework.api

interface TifManager {

    suspend fun getAllChannels(): List<ChannelModel>
}