package com.training.hilt_roomdatabase.core_presentation.ui.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.training.hilt_roomdatabase.R
import com.training.hilt_roomdatabase.core_data.local.entity.NoteEntity
import com.training.hilt_roomdatabase.core_presentation.viewmodel.AddNoteViewModel
import com.training.hilt_roomdatabase.databinding.FragmentAddNoteBinding
import com.training.hilt_roomdatabase.extensions.getCurrentDateTime
import com.training.hilt_roomdatabase.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class AddNoteFragment : Fragment() {
    private lateinit var binding : FragmentAddNoteBinding

    var selectedNoteColor :Int= 0
    private val addNoteViewModel : AddNoteViewModel by viewModels()

    private var note: NoteEntity?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddNoteBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMiscellaneousLayout()

        binding.apply {
            icDoneImg.setOnClickListener{
                var title = titleEdt.text.toString().trim()
                var desc = descriptionEdt.text.toString().trim()

                if (title.isNotEmpty() || desc.isNotEmpty()){
                    note = NoteEntity(
                        id = 0,
                        title = title,
                        desc = desc,
                        dateTime = getCurrentDateTime(),
                        color = selectedNoteColor,
                        imagePath = imagePathUri.toString()
                        )
                    note?.let {
                        addNoteViewModel.insertNote(it)
                    }
                    findNavController().popBackStack()
                }else{
                    Snackbar.make(it,"title and description can't empty at the same task", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun initMiscellaneousLayout(){
        var miscellaneous = view?.findViewById<LinearLayout>(R.id.miscellaneous_layout)
        miscellaneous?.let {
            var bottomSheet = BottomSheetBehavior.from(it)

            binding.miscellaneousLayout
                .apply {
                    miscellaneousTv
                        .setOnClickListener{
                            if (bottomSheet.state != BottomSheetBehavior.STATE_EXPANDED){
                                bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
                            }else{
                                bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
                            }
                        }
                    handleMiscellaneousUIEvents()
                }
        }
    }

    private fun handleMiscellaneousUIEvents() {
        binding.miscellaneousLayout
            .apply {
                viewColor1
                    .setOnClickListener{

                        selectedNoteColor = ContextCompat
                            .getColor(requireContext(),
                                R.color.colorDefaultNoteColor)
                        setSubtitleIndicatorColor(
                            selectedNoteColor
                        )

                        imageColor1.visibility = View.VISIBLE
                        imageColor2.visibility = View.GONE
                        imageColor3.visibility = View.GONE
                        imageColor4.visibility = View.GONE
                        imageColor5.visibility = View.GONE
                    }

                viewColor2
                    .setOnClickListener{
                        selectedNoteColor = ContextCompat
                            .getColor(requireContext(),
                                R.color.colorNoteColor2)
                        setSubtitleIndicatorColor(
                            selectedNoteColor
                        )

                        imageColor1.visibility = View.GONE
                        imageColor2.visibility = View.VISIBLE
                        imageColor3.visibility = View.GONE
                        imageColor4.visibility = View.GONE
                        imageColor5.visibility = View.GONE
                    }

                viewColor3
                    .setOnClickListener{
                        selectedNoteColor = ContextCompat
                            .getColor(requireContext(),
                                R.color.colorNoteColor3)
                        setSubtitleIndicatorColor(
                            selectedNoteColor
                        )

                        imageColor1.visibility = View.GONE
                        imageColor2.visibility = View.GONE
                        imageColor3.visibility = View.VISIBLE
                        imageColor4.visibility = View.GONE
                        imageColor5.visibility = View.GONE
                    }

                viewColor4
                    .setOnClickListener{
                        selectedNoteColor = ContextCompat
                            .getColor(requireContext(),
                                R.color.colorNoteColor4)
                        setSubtitleIndicatorColor(
                            selectedNoteColor
                        )

                        imageColor1.visibility = View.GONE
                        imageColor2.visibility = View.GONE
                        imageColor3.visibility = View.GONE
                        imageColor4.visibility = View.VISIBLE
                        imageColor5.visibility = View.GONE
                    }

                viewColor5.setOnClickListener{
                    selectedNoteColor = ContextCompat
                        .getColor(requireContext(),
                            R.color.colorNoteColor5)
                    setSubtitleIndicatorColor(
                        selectedNoteColor
                    )

                    imageColor1.visibility = View.GONE
                    imageColor2.visibility = View.GONE
                    imageColor3.visibility = View.GONE
                    imageColor4.visibility = View.GONE
                    imageColor5.visibility = View.VISIBLE
                }

                addImageLayout
                    .setOnClickListener{
                        // take permission to load image
                        checkPermission()
                    }
            }
    }




    var imagePathUri : Uri? = null
    val pickImageLauncher =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ){
            it?.let {uri->
                // uplaod image from here
                // set image too
                imagePathUri = uri
                binding.apply {
                    imageImg
                        .setImageURI(imagePathUri)
                }
            }
        }



    fun pickImage(){
        pickImageLauncher.launch("image/*")
    }

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ){isGranted->
            if (isGranted){
                // pick image from gallery

            }else{
                // show message to user to clarify if you want to add image accept this permission
                showToast(requireContext(),
                    "Gallery permission is denied," +
                            "to add image you should to agree this permission")
            }
        }
    fun checkPermission(){
        if (ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )!= PackageManager.PERMISSION_GRANTED){
            // permission denied
            // request permission
            requestPermissionLauncher.launch(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )

        }else{
            // permission granted
            // pick image
            pickImage()
        }
    }

    fun setSubtitleIndicatorColor(
        selectedNoteColor:Int
    ){
        binding
            .subtitleIndicator
            .setBackgroundColor(selectedNoteColor)
    }

}