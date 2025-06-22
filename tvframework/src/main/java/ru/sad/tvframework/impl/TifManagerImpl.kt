package ru.sad.tvframework.impl

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.media.tv.TvContract
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sad.tvframework.api.ChannelModel
import ru.sad.tvframework.api.TifManager


internal class TifManagerImpl(
    private val context: Context
) : TifManager {

    private val uri = TvContract.Channels.CONTENT_URI
    private val contentResolver: ContentResolver = context.contentResolver

    val projection = arrayOf(
        TvContract.Channels.COLUMN_DISPLAY_NAME,
        TvContract.Channels.COLUMN_DISPLAY_NUMBER,
        TvContract.Channels.COLUMN_INPUT_ID,
        TvContract.Channels._ID
    )

    @SuppressLint("Range")
    override suspend fun getAllChannels(): List<ChannelModel> = withContext(Dispatchers.Default) {
        val cursor = contentResolver.query(uri, projection, null, null, null)

        buildList {
            cursor?.use { c ->
                val nameIndex = c.getColumnIndex(TvContract.Channels.COLUMN_DISPLAY_NAME)
                val numberIndex = c.getColumnIndex(TvContract.Channels.COLUMN_DISPLAY_NUMBER)
                val inputIdIndex = c.getColumnIndex(TvContract.Channels.COLUMN_INPUT_ID)
                val channelIdIndex = c.getColumnIndex(TvContract.Channels._ID)

                while (c.moveToNext()) {
                    val name = c.getString(nameIndex)
                    val number = c.getString(numberIndex)
                    val inputId = c.getString(inputIdIndex)
                    val id = c.getLong(channelIdIndex)

                    val channelUri = TvContract.buildChannelUri(id)

                    val finalChannelModel = ChannelModel(
                        tvInputId = inputId,
                        name = name,
                        number = number,
                        channelUri = channelUri.toString(),
                        id = id.toString()
                    )

                    add(finalChannelModel)
                }
            } ?: errorChannelNotFound()
        }

    }

    private fun errorChannelNotFound() {
        Toast.makeText(context, CHANNELS_NOT_FOUND, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val CHANNELS_NOT_FOUND = "No, bro, no"
    }


}