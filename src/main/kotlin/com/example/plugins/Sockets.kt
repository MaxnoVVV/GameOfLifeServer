package com.example.plugins

import com.Game.Game
import com.Game.GameStateData
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.Identity.decode
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import java.time.Duration

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    routing {
        webSocket("/game") { // websocketSession
            var currentState: GameStateData
            var game: Game = Game()
            incoming.consumeEach { frame ->
                currentState = Cbor.decodeFromByteArray<GameStateData>(frame.data)
                game.setParamsFromData(currentState)
                send(Cbor.encodeToByteArray(game.makeStep()))
            }
        }
        webSocket("/test") {
            send("Connected")
            println("connected")
        }
    }
}
