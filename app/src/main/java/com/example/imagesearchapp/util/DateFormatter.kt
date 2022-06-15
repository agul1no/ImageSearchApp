package com.example.imagesearchapp.util

import java.text.SimpleDateFormat

class DateFormatter {

    companion object{

        const val DATE_FORMAT_DOWNLOAD = "dd-MM-yyyy_HH:mm:ss"

        fun Long.dateFormatterDownload(): String {
            val formatter = SimpleDateFormat(DATE_FORMAT_DOWNLOAD)
            return formatter.format(this)
        }
    }
}