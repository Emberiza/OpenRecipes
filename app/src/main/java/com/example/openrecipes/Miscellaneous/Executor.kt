package com.example.openrecipes.Miscellaneous

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class Executor private constructor() {
    //executor vytvori novy Thread Pool o 3 threads
    private val mNetworkIO =
        Executors.newScheduledThreadPool(3)

    // funkce vrati jiz vytvoreny Thread Pool, pouzivano hlavne v RecipeClientApi.kt
    fun networkIO(): ScheduledExecutorService {
        return mNetworkIO
    }

    companion object {
        val instance by lazy {
            Executor()
        }
    }
}
