package com.proj.movieappreciate.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proj.movieappreciate.data.dataSource.model.UserDTO
import com.proj.movieappreciate.data.dataSource.model.LoginResponse
import com.proj.movieappreciate.data.dataSource.model.SignUpData
import com.proj.movieappreciate.data.dataSource.model.SignUpResponse
import com.proj.movieappreciate.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor(private val authRepository : AuthRepository) : ViewModel(){

    private val _signUpResponse = MutableLiveData<Result<SignUpResponse>>()
    private val _loginResponse = MutableLiveData<Result<LoginResponse>>()
    val signUpResponse: LiveData<Result<SignUpResponse>> get() = _signUpResponse
    val loginResponse: LiveData<Result<LoginResponse>> get() = _loginResponse
    fun signUp(uid : String, type : String, profileURL : String) {


        viewModelScope.launch {
            val data = SignUpData(uid, type, profileURL)
            try {
                val result = authRepository.signUp(uid, type, profileURL)
                _signUpResponse.value = result
            } catch (e: Exception) {
                _signUpResponse.value = Result.failure(e)
            }
        }
    }

    fun login(uid : String, type : String, profileURL : String) {
        viewModelScope.launch {
            val data = UserDTO(uid, type)
            try{
                val result = authRepository.login(uid, type)
                if(result.isFailure){
                    if(result.exceptionOrNull() is AuthRepository.SignUpException){
                        signUp(uid, type, profileURL)
                    }
                }
                else{
                    _loginResponse.value = result
                    Log.d("Result", "login: $result")
                }

            } catch (e : Exception){
                Log.e("login ERROR", "login: ${e.message}" )

            }
        }
    }
}