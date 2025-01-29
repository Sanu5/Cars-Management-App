package com.example.carmanageapp

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carmanageapp.models.UserRequest
import com.example.carmanageapp.models.UserResponse
import com.example.carmanageapp.repository.UserRepository
import com.example.carmanageapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    val userResponseLiveData : LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponseLiveData

    fun registerUser(userRequest: UserRequest){
        viewModelScope.launch{
            userRepository.registerUser(userRequest)
        }
    }

    fun loginUser(userRequest: UserRequest){
        viewModelScope.launch{
            userRepository.loginUser(userRequest)
        }

    }

    fun validate(username : String, email : String, password : String, isLogin : Boolean) : Pair<Boolean, String>{
        var result = Pair(true, "")

        if(TextUtils.isEmpty(email) || (!isLogin && TextUtils.isEmpty(username)) || TextUtils.isEmpty(password)){
            result = Pair(false, "Please provide credentials")
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            result = Pair(false, "Please provide valid emails")
        }

        else if(password.length <= 5){
            result = Pair(false, "Password length should be greater than 5")
        }

        return result
    }
}