package com.github.akvast.locations.ui.vm

import android.text.format.DateUtils
import com.github.akvast.locations.App
import com.github.akvast.locations.database.entities.UserActivity
import com.google.android.gms.location.DetectedActivity
import java.text.DateFormat

class UserActivityViewModel(private val userActivity: UserActivity) {

    fun type() = when (userActivity.type) {
        DetectedActivity.IN_VEHICLE -> "IN_VEHICLE"
        DetectedActivity.ON_BICYCLE -> "ON_BICYCLE"
        DetectedActivity.ON_FOOT -> "ON_FOOT"
        DetectedActivity.STILL -> "STILL"
        DetectedActivity.TILTING -> "TILTING"
        DetectedActivity.WALKING -> "WALKING"
        DetectedActivity.RUNNING -> "RUNNING"
        else -> "UNKNOWN"
    }

    fun confidence() = "${userActivity.confidence}%"

    fun date() = DateUtils.formatDateTime(
            App.context,
            userActivity.date.time,
            DateUtils.FORMAT_SHOW_DATE or
                    DateUtils.FORMAT_SHOW_TIME or
                    DateUtils.FORMAT_NUMERIC_DATE)!!

}