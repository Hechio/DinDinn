package com.stevehechio.apps.dindinnassigment.utils

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor

/**
 * Created by stevehechio on 6/29/21
 */
object DateUtils {
    private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
    fun formatDateStr(strDate: String): Long {
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        sdf.timeZone=TimeZone.getTimeZone(TimeZone.getDefault().id)
        return sdf.parse(strDate)?.time ?: 0
    }

    fun formatGMTDateStr(strDate: String): String {
        val inputFormatter: DateFormat =
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        inputFormatter.timeZone = TimeZone.getTimeZone(TimeZone.getDefault().id)
        val outputFormatter: DateFormat =
            SimpleDateFormat("hh:mm aa", Locale.getDefault())
        return outputFormatter.format(Objects.requireNonNull(inputFormatter.parse(strDate))) ?: ""
    }

   fun secondsToMinutes(millisUntilFinished: Long): String {
        if (millisUntilFinished == 0L) {
            return ""
        }

        val seconds = ceil((millisUntilFinished / 1000).toDouble()).toInt()
        val min = floor((seconds / 60).toDouble()).toInt()
        val secs = seconds % 60
        return if (min > 0 && secs > 0) {
            "$min min $secs s"
        } else if (min > 0) {
            "$min min $secs s"
        } else {
            "$seconds s"
        }
    }
}