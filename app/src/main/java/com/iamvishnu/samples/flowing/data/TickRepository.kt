package com.iamvishnu.samples.flowing.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

object TickRepository {

    var currentTick = 0

    private val tickerFlow = flow {

        while (true) {
            println("Ticking in Repo $currentTick")
            emit(currentTick++)

            delay(1000L)
        }

    }

    private val scope = CoroutineScope(Dispatchers.Default)

    val ticker = tickerFlow
        .stateIn(
            scope,
            SharingStarted.WhileSubscribed(5000),
            currentTick
        )


}