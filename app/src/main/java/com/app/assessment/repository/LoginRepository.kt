package com.app.assessment.repository

import com.app.assessment.model.LoginRequest
import com.app.assessment.model.LoginResponse
import com.app.assessment.network.ApiService
import retrofit2.Callback
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val apiService: ApiService) {

    fun fetchLogin(username: String?,password: String?, callback: Callback<LoginResponse>) {
        apiService.login(LoginRequest(username!!, password!!)).enqueue(callback)
    }
}