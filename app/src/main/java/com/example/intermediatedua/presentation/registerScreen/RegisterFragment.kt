package com.example.intermediatedua.presentation.registerScreen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.intermediatedua.R
import com.example.intermediatedua.databinding.FragmentRegisterBinding

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding : FragmentRegisterBinding? = null
    private val binding: FragmentRegisterBinding
        get() = _binding!!

    private val registerViewModel by viewModels<RegisterViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container ,false)
        binding.btnRegister.setOnClickListener {
            val name = binding.edNameRegister.text.toString()
            val email = binding.edEmailRegister.text.toString()
            val password = binding.edPasswordRegister.text.toString()
            lifecycleScope.launch {
                when(val response = registerViewModel.register(name,email, password)){
                    is RegisterViewModel.RegisterResult.Success -> {
                        Toast.makeText(requireContext(), response.data.message,Toast.LENGTH_SHORT).show()
                        findNavController().navigate(RegisterFragmentDirections.actionRegisterScreenToLoginFragment())
                    }
                    is RegisterViewModel.RegisterResult.Error -> {
                        Toast.makeText(requireContext(), response.error,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enableRegisterButton()
        binding.tbRegister.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        registerViewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }
    }

    private fun enableRegisterButton(){
        binding.edNameRegister.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edNameRegister.error == null ){
                    isEnable()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.edEmailRegister.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edEmailRegister.error == null ){
                    isEnable()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.edPasswordRegister.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edPasswordRegister.error == null ){
                    isEnable()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }
    private fun isEnable(){
        val name = binding.edNameRegister.text
        val email = binding.edEmailRegister.text
        val password = binding.edPasswordRegister.text
        binding.btnRegister.isEnabled =
            !name.isNullOrEmpty() && !email.isNullOrEmpty() && !password.isNullOrEmpty()
    }
    private fun isLoading(isLoading : Boolean){
        binding.pbRegister.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}