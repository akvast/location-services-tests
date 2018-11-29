package com.github.akvast.locations.receivers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.github.akvast.locations.R
import com.github.akvast.locations.database.Database
import com.github.akvast.locations.database.entities.LocationUpdate
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LocationReceiver : BroadcastReceiver(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    companion object {
        const val CHANNEL_ID = "locations"
        const val CHANNEL_NAME = "Location updates"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (!LocationResult.hasResult(intent))
            return

        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationManager.createNotificationChannel(channel)
        }

        val locationResult = LocationResult.extractResult(intent)
        val location = locationResult.lastLocation

        launch(IO) {
            Database.getLocationUpdateDao()
                    .insert(LocationUpdate().apply {
                        latitude = location.latitude
                        longitude = location.longitude
                    })
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_location_on_black_24dp)
                .setColor(context.resources.getColor(R.color.colorPrimary))
                .setContentTitle(context.getString(R.string.app_name))
                .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_VIBRATE)
                .setLights(Color.RED, 500, 2000)
                .setContentText("Location update: ${location.latitude},${location.longitude}")
                .setAutoCancel(true)

        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }

}