package com.example.openrecipes.Miscellaneous

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class Executor private constructor() {
    private val mNetworkIO =
        Executors.newScheduledThreadPool(3)

    fun networkIO(): ScheduledExecutorService {
        return mNetworkIO
    }

    companion object {
        val instance by lazy {
            Executor()
        }
    }
}
