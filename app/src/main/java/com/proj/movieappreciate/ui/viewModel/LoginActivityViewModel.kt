package com.proj.movieappreciate.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.proj.movieappreciate.data.dataSource.model.SignUpData
import com.proj.movieappreciate.data.dataSource.model.SignUpResponse
import com.proj.movieappreciate.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor(private val authRepository : AuthRepository) : ViewModel(){
    private val _signUpResponse = MutableLiveData<Result<SignUpResponse>>()
    val signUpResponse: LiveData<Result<SignUpResponse>> get() = _signUpResponse
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
}