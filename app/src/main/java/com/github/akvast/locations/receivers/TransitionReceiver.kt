package com.github.akvast.locations.receivers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.github.akvast.locations.R
import com.github.akvast.locations.database.Database
import com.github.akvast.locations.database.entities.UserActivity
import com.github.akvast.locations.database.entities.UserActivityTransition
import com.github.akvast.locations.ui.vm.UserActivityTransitionViewModel
import com.google.android.gms.location.ActivityRecognitionResult
import com.google.android.gms.location.ActivityTransitionResult
import com.google.android.gms.location.DetectedActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class TransitionReceiver : BroadcastReceiver(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    companion object {
        const val CHANNEL_ID = "activity"
        const val CHANNEL_NAME = "Activity Recognition"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationManager.createNotificationChannel(channel)
        }

        val recognitionResult = ActivityRecognitionResult.extractResult(intent)
        recognitionResult?.mostProbableActivity?.let {
            if (it.type != DetectedActivity.STILL) {
                launch(IO) {
                    Database.getUserActivityDao()
                            .insert(UserActivity().apply {
                                type = it.type
                                confidence = it.confidence
                            })
                }
            }
        }

        val transitionResult = ActivityTransitionResult.extractResult(intent)
        launch(IO) {
            transitionResult?.transitionEvents?.forEach {
                Database.getUserActivityTransitionDao()
                        .insert(UserActivityTransition().apply {
                            activityType = it.activityType
                            transitionType = it.transitionType
                            elapsedRealTimeNanos = it.elapsedRealTimeNanos
                        })
            }
        }

        transitionResult?.transitionEvents?.forEach {
            val viewModel = UserActivityTransitionViewModel(UserActivityTransition().apply {
                activityType = it.activityType
                transitionType = it.transitionType
            })
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_location_on_black_24dp)
                    .setColor(context.resources.getColor(R.color.colorPrimary))
                    .setContentTitle(context.getString(R.string.app_name))
                    .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_VIBRATE)
                    .setLights(Color.RED, 500, 2000)
                    .setContentText("${viewModel.activityType()} ${viewModel.transitionType()} at ${viewModel.date()}")
                    .setAutoCancel(true)

            notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }

}