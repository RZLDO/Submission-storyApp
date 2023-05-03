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
import com.example.intermediatedua.R
import com.example.intermediatedua.data.home.ListStoryItem
import com.example.intermediatedua.data.home.StoryResponse
import com.example.intermediatedua.data.local.UserPreferences
import com.example.intermediatedua.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding : FragmentHomeBinding
        get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel>()
    @Inject lateinit var userPreferences: UserPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            homeViewModel.fetchStory()
        }
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddStoryFragment())
        }

        homeViewModel.storyResponse.observe(viewLifecycleOwner){
            setStory(it)
        }
        homeViewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }
        binding.homeToolbar.setOnMenuItemClickListener{menuItem->
            when(menuItem.itemId){
                R.id.btn_logout -> {
                    userPreferences.clearUser()
                    findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                    true
                }
                R.id.btn_to_maps -> {
                    findNavController().navigate(R.id.action_homeFragment_to_mapsFragment)
                    true
                }
                else ->{
                    false
                }
            }
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