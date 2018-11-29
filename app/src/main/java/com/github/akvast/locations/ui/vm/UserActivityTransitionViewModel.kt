package com.github.akvast.locations.ui.vm

import android.text.format.DateUtils
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

    fun date() = DateUtils.formatSameDayTime(
            transition.date.time,
            System.currentTimeMillis(),
            DateFormat.MEDIUM,
            DateFormat.SHORT)!!

}