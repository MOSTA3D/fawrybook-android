package com.mosta3d.fawrybook.shared.network

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@Singleton
class NetworkEventBus @Inject constructor() {
    private val _events = MutableSharedFlow<NetworkEvent>(
        replay = 0,
        extraBufferCapacity = 8
    )

    val events: SharedFlow<NetworkEvent> = _events.asSharedFlow()

    suspend fun emit(event: NetworkEvent) {
        _events.emit(event)
    }

    fun tryEmit(event: NetworkEvent): Boolean {
        return _events.tryEmit(event)
    }
}


