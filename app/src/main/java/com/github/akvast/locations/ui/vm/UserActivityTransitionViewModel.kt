package com.github.akvast.locations.ui.vm

import android.text.format.DateUtils
import com.github.akvast.locations.App
import com.github.akvast.locations.database.entities.UserActivityTransition
import com.google.android.gms.location.ActivityTransition
import com.google.android.gms.location.DetectedActivity
import java.text.DateFormat

class UserActivityTransitionViewModel(private val transition: UserActivityTransition) {

    fun activityType() = when (transition.activityType) {
        DetectedActivity.IN_VEHICLE -> "IN_VEHICLE"
        DetectedActivity.ON_BICYCLE -> "ON_BICYCLE"
        DetectedActivity.ON_FOOT -> "ON_FOOT"
        DetectedActivity.STILL -> "STILL"
        DetectedActivity.TILTING -> "TILTING"
        DetectedActivity.WALKING -> "WALKING"
        DetectedActivity.RUNNING -> "RUNNING"
        else -> "UNKNOWN"
    }

    fun transitionType() = when (transition.transitionType) {
        ActivityTransition.ACTIVITY_TRANSITION_ENTER -> "ENTER"
        ActivityTransition.ACTIVITY_TRANSITION_EXIT -> "EXIT"
        else -> "UNKNOWN"
    }

    fun date() = DateUtils.formatDateTime(
            App.context,
            transition.date.time,
            DateUtils.FORMAT_SHOW_DATE or
                    DateUtils.FORMAT_SHOW_TIME or
                    DateUtils.FORMAT_NUMERIC_DATE)!!

    fun realDate() = DateUtils.formatDateTime(
            App.context,
            transition.date.time - transition.elapsedRealTimeNanos / 1000000L,
            DateUtils.FORMAT_SHOW_TIME)!!

}