package com.github.akvast.transitiontest.utils

import android.app.Activity
import androidx.core.app.ActivityCompat

fun Activity.withPermission(permission: String,
                            requestCode: Int,
                            onFailed: () -> Unit,
                            onSucceed: () -> Unit) {

    if (hasPermission(permission))
        return onSucceed.invoke()

    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
        ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
    } else {
        onFailed.invoke()
    }
}