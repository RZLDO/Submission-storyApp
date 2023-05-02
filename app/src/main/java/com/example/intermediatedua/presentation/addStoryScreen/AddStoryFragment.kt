package com.example.intermediatedua.presentation.addStoryScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.intermediatedua.R
import com.example.intermediatedua.databinding.FragmentAddStoryBinding

class AddStoryFragment : Fragment() {
    private var _binding : FragmentAddStoryBinding? = null
    private val binding : FragmentAddStoryBinding
        get() = _binding!!
    private val addStoryViewModel by viewModels<AddStoryViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddStoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}