package com.Game

import kotlinx.serialization.Serializable

@Serializable
data class ServerResponseData(var isActed : Boolean,val table: Array<Array<Int>>?)
