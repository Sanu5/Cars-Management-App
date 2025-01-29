package com.example.carmanageapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.carmanageapp.databinding.FragmentRegisterBinding
import com.example.carmanageapp.models.UserRequest
import com.example.carmanageapp.utils.NetworkResult
import com.example.carmanageapp.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class registerFragment : Fragment() {

    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by activityViewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        if(tokenManager.getToken() != null){
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvAlreadyHaveAccount.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnSignUp.setOnClickListener{
            val validateResult = validateUserInput()
            if(validateResult.first){
                val userRequest = getUserRequest()

                authViewModel.registerUser(userRequest)
            }
            else{
                binding.txtError.text = validateResult.second
            }
        }


        bindObserver()
    }

    private fun getUserRequest(): UserRequest{
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val username = binding.etUsername.text.toString()

        return UserRequest(email, password, username)
    }

    private fun validateUserInput(): Pair<Boolean, String>{
        val userRequest = getUserRequest()

        return authViewModel.validate(userRequest.username, userRequest.email, userRequest.password, false)
    }

    private fun bindObserver() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, {
            binding.progressBar.isVisible = false
            when(it) {
                is NetworkResult.Success -> {

                    tokenManager.saveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }
                is NetworkResult.Error -> {
                    binding.txtError.text = it.message
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }
        override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}