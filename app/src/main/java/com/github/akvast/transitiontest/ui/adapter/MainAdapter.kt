package com.github.akvast.transitiontest.ui.adapter

import android.support.v4.widget.SwipeRefreshLayout
import com.github.akvast.mvvm.adapter.ViewModelAdapter
import com.github.akvast.transitiontest.BR
import com.github.akvast.transitiontest.R
import com.github.akvast.transitiontest.database.Database
import com.github.akvast.transitiontest.database.entities.UserActivity
import com.github.akvast.transitiontest.database.entities.UserActivityTransition
import com.github.akvast.transitiontest.ui.vm.UserActivityTransitionViewModel
import com.github.akvast.transitiontest.ui.vm.UserActivityViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class MainAdapter : ViewModelAdapter() {

    init {
        setHasStableIds(true)
        cell(UserActivityViewModel::class.java, R.layout.cell_user_activity, BR.vm)
        cell(UserActivityTransitionViewModel::class.java, R.layout.cell_user_activity_transition, BR.vm)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun reload(refreshLayout: SwipeRefreshLayout?) {
        Database.getUserActivityDao()
                .getAll()
                .zipWith(Database.getUserActivityTransitionDao().getAll(), Zip())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    beginUpdates()
                    items.clear()
                    items.addAll(it)
                    endUpdates()
                    refreshLayout?.isRefreshing = false
                }
    }

    private class Zip : BiFunction<List<UserActivity>, List<UserActivityTransition>, List<Any>> {

        override fun apply(activityList: List<UserActivity>, transitionList: List<UserActivityTransition>): List<Any> {
            return activityList
                    .union(transitionList)
                    .sortedByDescending {
                        when (it) {
                            is UserActivity -> it.date.time
                            is UserActivityTransition -> it.date.time
                            else -> throw IllegalStateException()
                        }
                    }
                    .map {
                        @Suppress("IMPLICIT_CAST_TO_ANY")
                        when (it) {
                            is UserActivity -> UserActivityViewModel(it)
                            is UserActivityTransition -> UserActivityTransitionViewModel(it)
                            else -> throw IllegalStateException()
                        }
                    }
        }

    }

}