package com.practicum.playlistmaker.utils

import java.text.SimpleDateFormat
import java.util.Locale

class TimeFormatter {
    companion object {
        fun convertTime(milis: Int): String {
            return SimpleDateFormat("mm:ss", Locale.getDefault()).format(milis)
        }
    }
}