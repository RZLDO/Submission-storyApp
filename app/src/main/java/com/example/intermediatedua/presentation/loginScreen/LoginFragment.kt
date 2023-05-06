
package com.example.intermediatedua.presentation.loginScreen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.intermediatedua.R
import com.example.intermediatedua.data.local.UserModel
import com.example.intermediatedua.data.local.UserPreferences
import com.example.intermediatedua.databinding.FragmentLoginBinding

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding : FragmentLoginBinding
        get() = _binding!!
    private val loginViewModel by viewModels<LoginViewModel>()
    @Inject lateinit var userPreferences: UserPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentLoginBinding.inflate(inflater,container, false )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEnableButton()
        binding.btnToRegister.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerScreen)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            lifecycleScope.launch {
                when (val result = loginViewModel.login(email,password)) {
                    is LoginViewModel.LoginResult.Success -> {
                        val userModel = UserModel(
                            result.data.loginResult.userId,
                            result.data.loginResult.name,
                            result.data.loginResult.token
                        )
                        userPreferences.saveUser(userModel)
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                    }
                    is LoginViewModel.LoginResult.Error -> {
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            loginViewModel.isLoading.observe(viewLifecycleOwner){
                Log.d("CEK LOADING ", it.toString())
                isLoading(it)
            }
        }

    }
    private fun setEnableButton(){
        binding.edEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edEmail.error == null ){
                    isEnable()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.edPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edPassword.error == null ){
                    isEnable()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }
    private fun isEnable(){
        val name = binding.edEmail.text
        val email = binding.edPassword.text
        binding.btnLogin.isEnabled =
            !name.isNullOrEmpty() && !email.isNullOrEmpty()
    }
    private fun isLoading(isLoading : Boolean ){
        binding.pbLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnLogin.isEnabled = !isLoading
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}