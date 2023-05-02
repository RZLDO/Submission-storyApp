package com.example.intermediatedua.presentation.storyDetailScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.intermediatedua.R
import com.example.intermediatedua.data.detailStory.Story
import com.example.intermediatedua.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding : FragmentDetailBinding? = null
    private val binding : FragmentDetailBinding
        get() = _binding!!
    private val detailViewModel by viewModels<DetailViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

            val id = DetailFragmentArgs.fromBundle(arguments as Bundle).id
            lifecycleScope.launch {
                when(val result = detailViewModel.detailStory(id)) {
                    is DetailViewModel.DetailResult.Success->{
                        val story = result.data
                        setData(story.story)
                    }
                    is DetailViewModel.DetailResult.Error -> {
                        val error = result.error
                    }
                    else -> {}
                }
            }

        detailViewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setData(dataStory : Story){
        Glide.with(this)
            .load(dataStory.photoUrl)
            .placeholder(R.drawable.baseline_image_24)
            .into(binding.ivImageDetail)

        binding.tvUsernameDetail.text = dataStory.name
        binding.tvDescriptionDetail.text = dataStory.description
    }
    private fun isLoading(it: Boolean) {
        binding.proggresBarDetail.visibility = if (it) View.VISIBLE  else View.GONE
    }

}