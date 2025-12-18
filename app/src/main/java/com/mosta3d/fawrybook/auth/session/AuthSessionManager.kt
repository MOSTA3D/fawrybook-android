package com.mosta3d.fawrybook.auth.session

import com.mosta3d.fawrybook.shared.di.ApplicationScope
import com.mosta3d.fawrybook.shared.network.NetworkEvent
import com.mosta3d.fawrybook.shared.network.NetworkEventBus
import com.mosta3d.fawrybook.shared.network.TokenStore
import com.mosta3d.fawrybook.shared.state.AppState
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Singleton
class AuthSessionManager @Inject constructor(
    private val eventBus: NetworkEventBus,
    private val tokenStore: TokenStore,
    @ApplicationScope private val scope: CoroutineScope,
) {
    init {
        scope.launch {
            eventBus.events.collect { event ->
                when (event) {
                    NetworkEvent.Unauthorized,
                    is NetworkEvent.AuthExpired -> {
                        tokenStore.clear()
                        AppState.clearAuthData()
                    }
                }
            }
        }
    }
}


