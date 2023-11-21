package com.example.emiencecodetest.api

import com.example.emiencecodetest.MatchResponse
import com.example.emiencecode.Utils.Constants
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST(Constants.URL)
    suspend fun matchApi(): Response<MatchResponse>
}