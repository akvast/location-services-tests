package com.github.akvast.transitiontest.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.akvast.mvvm.utils.contentView
import com.github.akvast.transitiontest.R
import com.github.akvast.transitiontest.databinding.ActivityMainBinding
import com.github.akvast.transitiontest.receivers.LocationReceiver
import com.github.akvast.transitiontest.ui.adapter.MainAdapter
import com.github.akvast.transitiontest.utils.openApplicationSettings
import com.github.akvast.transitiontest.utils.withPermission
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by contentView(R.layout.activity_main)

    private val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activity = this
        binding.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.reload(binding.swipeRefreshLayout)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.reload()

        requestLocationUpdates()
    }

    // Private:

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {
        withPermission(Manifest.permission.ACCESS_FINE_LOCATION, 0, this::requestAccessCoarseLocationPermission) {
            val intent = Intent(this, LocationReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val locationRequest = LocationRequest()
                    .setInterval(30000L)
                    .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)

            val client = LocationServices.getFusedLocationProviderClient(this)
            client.requestLocationUpdates(locationRequest, pendingIntent)
        }
    }

    private fun requestAccessCoarseLocationPermission() {
        AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Location permits required")
                .setPositiveButton("Settings") { _, _ -> openApplicationSettings() }
                .show()
    }

}