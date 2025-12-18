package com.mosta3d.fawrybook.shared.network

interface TokenRefresher {
    /**
     * @return true if refresh succeeded and tokens were updated, false otherwise.
     */
    suspend fun refresh(): Boolean
}


