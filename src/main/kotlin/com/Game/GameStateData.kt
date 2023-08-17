package com.Game

import kotlinx.serialization.Serializable

@Serializable
data class GameStateData(val table: Array<Array<Int>>,
                         val height: Int,
                         val width: Int,
                         val neightborsToBorn: Int,
                         val neightborsToLiveLeftbound: Int,
                         val neightborsToLiveRightbound: Int)
