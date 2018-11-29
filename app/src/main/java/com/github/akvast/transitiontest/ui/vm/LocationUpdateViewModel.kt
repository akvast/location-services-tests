package com.github.akvast.transitiontest.ui.vm

import android.text.format.DateUtils
import com.github.akvast.transitiontest.database.entities.LocationUpdate
import java.text.DateFormat

class LocationUpdateViewModel(val locationUpdate: LocationUpdate) {

    val location = "${locationUpdate.latitude},${locationUpdate.longitude}"

    fun date() = DateUtils.formatSameDayTime(
            locationUpdate.date.time,
            System.currentTimeMillis(),
            DateFormat.MEDIUM,
            DateFormat.SHORT)!!

}