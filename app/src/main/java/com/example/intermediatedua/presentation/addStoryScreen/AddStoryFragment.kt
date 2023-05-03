package com.example.intermediatedua.presentation.addStoryScreen

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.intermediatedua.databinding.FragmentAddStoryBinding
import com.example.intermediatedua.utils.createCustomTempFile
import com.example.intermediatedua.utils.reduceFileImage
import com.example.intermediatedua.utils.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
@AndroidEntryPoint
class AddStoryFragment : Fragment() {
    private var _binding : FragmentAddStoryBinding? = null
    private val binding : FragmentAddStoryBinding
        get() = _binding!!
    private val addStoryViewModel by viewModels<AddStoryViewModel>()
    private var getFile : File? = null
    companion object{
        private const val REQUEST_CAMERA_PERMISSION = 1
    }
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
        binding.toolbarAddStory.setNavigationOnClickListener {
            findNavController().navigate(AddStoryFragmentDirections.actionAddStoryFragmentToHomeFragment())
        }
        binding.btnToCamera.setOnClickListener {
            val cameraPermission = ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CAMERA
            )
            if (cameraPermission != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            }else{
                startTakePhoto()
            }
        }
        binding.btnToGalery.setOnClickListener {
            openGallery()
        }
        binding.btnAddStory.setOnClickListener {
            if (getFile != null){
                val description = binding.edContentDescription.text.toString().toRequestBody("text/plain".toMediaType())
                val file = reduceFileImage(getFile as File)
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageMultipart : MultipartBody.Part = MultipartBody.Part.createFormData(
                    "photo",
                    file.name,
                    requestImageFile
                )
                (activity as AppCompatActivity).supportActionBar?.title = "Detail Story"
                lifecycleScope.launch {
                    val result = addStoryViewModel.addStory(description, imageMultipart)
                    when (result){
                        is AddStoryViewModel.AddStoryResult.Success -> {
                            val builder : AlertDialog.Builder = AlertDialog.Builder(requireContext())
                            builder.setTitle("Message")
                            builder.setMessage(result.data.message)
                            builder.setPositiveButton("OK"){ dialog, _ ->
                                findNavController().navigate(AddStoryFragmentDirections.actionAddStoryFragmentToHomeFragment())
                                dialog.dismiss()
                            }
                            val dialog : AlertDialog = builder.create()
                            dialog.show()
                        }
                        is AddStoryViewModel.AddStoryResult.Error -> {
                            val builder : AlertDialog.Builder = AlertDialog.Builder(requireContext())
                            builder.setTitle("Message")
                            builder.setMessage(result.message)
                            builder.setPositiveButton("OK"){ dialog, _ ->
                                findNavController().navigate(AddStoryFragmentDirections.actionAddStoryFragmentToHomeFragment())
                                dialog.dismiss()
                            }
                            val dialog : AlertDialog = builder.create()
                            dialog.show()
                        }
                        else -> {}
                    }
                }
            }else{
                Toast.makeText(requireContext(), "please add the image first.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent,"Choose a picture")
        launcherIntentGalery.launch(chooser)
    }
    private val launcherIntentGalery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == Activity.RESULT_OK){
            val selectedImg : Uri = it.data?.data as Uri
            selectedImg.let {Uri->
                val myFile = uriToFile(Uri,requireContext())
                getFile = myFile
                binding.ivReviewImage.setImageURI(Uri)
            }
        }
    }
    private lateinit var currentPhotoPath:String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val myFile = File(currentPhotoPath)
            val result = BitmapFactory.decodeFile(myFile.path)
            getFile = myFile
            binding.ivReviewImage.setImageBitmap(result)
        }
    }
    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)
        createCustomTempFile(requireActivity().application).also {
            val photoUri : Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.example.intermediateDua",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            launcherIntentCamera.launch(intent)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startTakePhoto()
            } else {
                Toast.makeText(requireContext(), "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}