package com.github.akvast.locations.ui.vm

import android.text.format.DateUtils
import com.github.akvast.locations.App
import com.github.akvast.locations.database.entities.LocationUpdate

class LocationUpdateViewModel(private val locationUpdate: LocationUpdate) {

    val location = "${locationUpdate.latitude},${locationUpdate.longitude}"

    fun date() = DateUtils.formatDateTime(
            App.context,
            locationUpdate.date.time,
            DateUtils.FORMAT_SHOW_DATE or
                    DateUtils.FORMAT_SHOW_TIME or
                    DateUtils.FORMAT_NUMERIC_DATE)!!

}