package com.example.intermediatedua.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intermediatedua.data.home.ListStoryItem
import com.example.intermediatedua.data.home.StoryResponse
import com.example.intermediatedua.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding : FragmentHomeBinding
        get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        lifecycleScope.launch {
            homeViewModel.fetchStory()
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddStoryFragment())
        }

        homeViewModel.storyResponse.observe(viewLifecycleOwner){
            setStory(it)
        }
        homeViewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun isLoading(it: Boolean) {
        binding.pbHome.visibility = if (it) View.VISIBLE else View.GONE
    }

    private fun setStory(it: List<ListStoryItem>) {
        binding.rvStory.setHasFixedSize(true)
        binding.rvStory.layoutManager = LinearLayoutManager(requireContext())

        val adapter = HomeAdapter(it)
        adapter.setOnItemClickListener { position ->
            val toDetail = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
            toDetail.id = it[position].id
            view?.findNavController()?.navigate(toDetail)
        }
        binding.rvStory.adapter = adapter
    }



}