package com.github.akvast.transitiontest.ui.adapter

import android.annotation.SuppressLint
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.akvast.mvvm.adapter.ViewModelAdapter
import com.github.akvast.transitiontest.BR
import com.github.akvast.transitiontest.R
import com.github.akvast.transitiontest.database.Database
import com.github.akvast.transitiontest.database.entities.LocationUpdate
import com.github.akvast.transitiontest.database.entities.UserActivity
import com.github.akvast.transitiontest.database.entities.UserActivityTransition
import com.github.akvast.transitiontest.ui.vm.LocationUpdateViewModel
import com.github.akvast.transitiontest.ui.vm.UserActivityTransitionViewModel
import com.github.akvast.transitiontest.ui.vm.UserActivityViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.coroutines.CoroutineContext

class MainAdapter : ViewModelAdapter(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    init {
        setHasStableIds(true)
        cell(UserActivityViewModel::class.java, R.layout.cell_user_activity, BR.vm)
        cell(UserActivityTransitionViewModel::class.java, R.layout.cell_user_activity_transition, BR.vm)
        cell(LocationUpdateViewModel::class.java, R.layout.cell_location_update, BR.vm)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("CheckResult")
    override fun reload(refreshLayout: SwipeRefreshLayout?) {
        launch {
            val activityList = async(IO) { Database.getUserActivityDao().getAll() }
            val transitionList = async(IO) { Database.getUserActivityTransitionDao().getAll() }
            val locationsList = async(IO) { Database.getLocationUpdateDao().getAll() }

            val newItems = merge(activityList.await(), transitionList.await(), locationsList.await())

            beginUpdates()
            items.clear()
            items.addAll(newItems)
            endUpdates()
            refreshLayout?.isRefreshing = false
        }
    }

    private fun merge(activityList: List<UserActivity>,
                      transitionList: List<UserActivityTransition>,
                      locationList: List<LocationUpdate>): List<Any> {

        return activityList
                .union(transitionList)
                .union(locationList)
                .sortedByDescending {
                    when (it) {
                        is UserActivity -> it.date.time
                        is UserActivityTransition -> it.date.time
                        is LocationUpdate -> it.date.time
                        else -> throw IllegalStateException()
                    }
                }
                .map {
                    @Suppress("IMPLICIT_CAST_TO_ANY")
                    when (it) {
                        is UserActivity -> UserActivityViewModel(it)
                        is UserActivityTransition -> UserActivityTransitionViewModel(it)
                        is LocationUpdate -> LocationUpdateViewModel(it)
                        else -> throw IllegalStateException()
                    }
                }
    }

}