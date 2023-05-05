package com.example.intermediatedua.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intermediatedua.R
import com.example.intermediatedua.data.local.UserPreferences
import com.example.intermediatedua.data.local.room.StoryResponseItems
import com.example.intermediatedua.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
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
        setStory()
    }


    private fun isLoading(it: Boolean) {
        binding.pbHome.visibility = if (it) View.VISIBLE else View.GONE
    }

    private fun setStory() {
        binding.rvStory.setHasFixedSize(true)
        binding.rvStory.layoutManager = LinearLayoutManager(requireContext())

        val adapter = HomeAdapter()
        binding.rvStory.adapter = adapter
        homeViewModel.story.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
        adapter.setOnItemClickListener(object : HomeAdapter.OnItemClickListener{
            override fun onItemClick(story: StoryResponseItems?) {
                val toDetail = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                toDetail.id = story!!.id
                view?.findNavController()?.navigate(toDetail)
            }
        })
    }



}