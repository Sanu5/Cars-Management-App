package com.example.carmanageapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.carmanageapp.databinding.FragmentLoginBinding
import com.example.carmanageapp.models.UserRequest
import com.example.carmanageapp.utils.NetworkResult
import com.example.carmanageapp.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class loginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by activityViewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.tvCreateAccount.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignIn.setOnClickListener {
            val validationResult = validateUserInput()
            if(validationResult.first){
                val userRequest = getUserRequest()

                authViewModel.loginUser(userRequest)
            }
            else{
                binding.txtError.text = validationResult.second
            }

        }

        binding.tvCreateAccount.setOnClickListener {
            findNavController().popBackStack()
        }

        bindObserver()


    }

    private fun getUserRequest(): UserRequest{
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        return UserRequest(email, password, "")
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val userRequest = getUserRequest()

        return authViewModel.validate(userRequest.username, userRequest.email, userRequest.password, true)
    }

    private fun bindObserver() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    //save the received token
                    tokenManager.saveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
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