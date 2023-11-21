package com.example.emiencecodetest.api

import com.example.emiencecodetest.MatchResponse
import com.example.emiencecode.base.BaseApiResponse
import com.example.emiencecodetest.base.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class ApiRepository @Inject constructor(
    private val apiService: ApiService
) : BaseApiResponse() {

    suspend fun matchDetails(): Flow<NetworkResult<MatchResponse>> {
        return flow<NetworkResult<MatchResponse>> {
            emit(safeApiCall { apiService.matchApi() })
        }.flowOn(Dispatchers.IO)
    }
}