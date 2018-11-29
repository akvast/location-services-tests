package com.github.akvast.locations.ui.vm

import android.text.format.DateUtils
import com.github.akvast.locations.database.entities.LocationUpdate
import java.text.DateFormat

class LocationUpdateViewModel(val locationUpdate: LocationUpdate) {

    val location = "${locationUpdate.latitude},${locationUpdate.longitude}"

    fun date() = DateUtils.formatSameDayTime(
            locationUpdate.date.time,
            System.currentTimeMillis(),
            DateFormat.MEDIUM,
            DateFormat.SHORT)!!

}