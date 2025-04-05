package com.app.assessment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.assessment.model.LoginRequest
import com.app.assessment.model.LoginResponse
import com.app.assessment.network.ApiService
import com.app.assessment.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {
    private val tokenLiveData = MutableLiveData<LoginResponse?>()

    fun login(username: String?, password: String?) {
        repository.fetchLogin(username!!, password!!,object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        tokenLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    tokenLiveData.postValue(null)
                }
            })
    }

    fun getTokenLiveData(): LiveData<LoginResponse?> {
        return tokenLiveData
    }
}

