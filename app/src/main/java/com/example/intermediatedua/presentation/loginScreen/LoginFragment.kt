
package com.example.intermediatedua.presentation.loginScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.intermediatedua.R
import com.example.intermediatedua.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding : FragmentLoginBinding
        get() = _binding!!
    private val loginViewModel by viewModels<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        val email = binding.edEmail.text.toString()
        val password = binding.edPassword.text.toString()
        binding.btnLogin.setOnClickListener {
            lifecycleScope.launch {
                loginViewModel.login(email,password)
            }
        }
        loginViewModel.loginResult.observe(viewLifecycleOwner){
            if (!it.error){

            }
        }
        _binding = FragmentLoginBinding.inflate(inflater,container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnToRegister.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerScreen)
        }

    }
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}