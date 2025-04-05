package com.app.assessment.network

import com.app.assessment.model.LoginRequest
import com.app.assessment.model.LoginResponse
import com.app.assessment.model.Transaction
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("transactions")
    suspend fun getTransactions(@Header("Authorization") token: String?): Response<List<Transaction>>
}

