package com.training.hilt_roomdatabase.core_presentation.ui.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.training.hilt_roomdatabase.R
import com.training.hilt_roomdatabase.core_data.local.entity.NoteEntity
import com.training.hilt_roomdatabase.core_presentation.viewmodel.AddNoteViewModel
import com.training.hilt_roomdatabase.databinding.FragmentAddNoteBinding
import com.training.hilt_roomdatabase.extensions.getCurrentDateTime
import com.training.hilt_roomdatabase.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
open class AddNoteFragment : Fragment() {
    private lateinit var binding : FragmentAddNoteBinding

    var selectedNoteColor :Int= 0
    private val addNoteViewModel : AddNoteViewModel by viewModels()

    private var note: NoteEntity?=null

    val args:AddNoteFragmentArgs by navArgs()

    private var noteId : Long = -1
    var recievedNote:NoteEntity?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteId = args.noteId
    }
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

        if (noteId>=0){
            // update data
            addNoteViewModel.getNote(noteId)
            observeNoteState()

            binding.apply {
                icDoneImg.setOnClickListener{
                    if(titleEdt.text.toString().trim().isNotEmpty() || descriptionEdt.text.toString().trim().isNotEmpty()){
                        note = createNote()

                        if (note == recievedNote){
                            Toast.makeText(context,"data not changed", Toast.LENGTH_SHORT).show()
                        }else{
                            note?.let {
                                addNoteViewModel.updateNote(it)
                            }
                        }
                    }else{
                        Toast.makeText(context,"title and description can't be empty",
                            Toast.LENGTH_SHORT).show()
                    }
                    findNavController().popBackStack()
                }

                icArrowBackImg.setOnClickListener{
                    recievedNote?.let {
                        addNoteViewModel.deleteNote(it)
                    }

                    findNavController().popBackStack()
                }

            }
        }else{
            // noteId = -1
            // add new data
            hanldleUIEvents()
        }

        initMiscellaneousLayout()

    }

    /*
    enable vcs
    clone (Import a project from a github)
    add
    commit
    push
    pull
    fetch
    update
    rollback
    annotate
    pull request

    Branches
    checkout
    create new
    Git commands using terminal
     */

    private fun observeNoteState() {
        viewLifecycleOwner.lifecycleScope.launch {
            addNoteViewModel.noteState.collectLatest {
                it?.let {
                    recievedNote = it
                }
                recievedNote?.let {
                    setViews(it)
                }
            }
        }
    }
    private fun setViews(note:NoteEntity) {
        binding
            .apply {
                titleEdt.setText(note.title)
                subtitleEdt.setText(note.subtitle)
                descriptionEdt.setText(note.desc)
                subtitleIndicator.setBackgroundColor(note.color?:0)
                linkTv.setText(note.webLink)
                Glide.with(imageImg)
                    .load(Uri.parse(note.imagePath))
                    .into(imageImg)
            }
    }





    private fun hanldleUIEvents() {
        binding.apply {
            icDoneImg.setOnClickListener{
                    createNote()
                    note?.let {
                        addNoteViewModel.insertNote(it)
                    }
                    findNavController().popBackStack()
                    }
            }
        }


    fun createNote():NoteEntity{
        binding.apply {
            var title = titleEdt.text.toString().trim()
            var desc = descriptionEdt.text.toString().trim()
            var subtitle = subtitleEdt.text.toString().trim()

            if (title.isNotEmpty() || desc.isNotEmpty()){
                note = NoteEntity(
                    id = 0,
                    title = if (title.isNotEmpty()) title else "",
                    desc = if (desc.isNotEmpty()) desc else "",
                    subtitle = if (subtitle.isNotEmpty())
                        subtitle else "",
                    dateTime = getCurrentDateTime(),
                    color = selectedNoteColor,
                    imagePath = imagePathUri.toString(),
                    webLink = if (linkTv.text.toString().isNotEmpty())
                        linkTv.text.toString() else ""
                )
        }
    }
        return note!!
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

                addWebLinkLayout
                    .setOnClickListener{
                        // will navigate to web link
                        // intent is responsible for transition
                        // show alert dialog
                        // get link from it
                        // when click cancel hide alert
                        // when click add show link in link tv and make it clickable
                        // will transition to destination link
                        displayAlertDialog()

                    }
            }
    }

    private fun displayAlertDialog() {
        binding
            .apply {
                var view = LayoutInflater.from(context)
                    .inflate(R.layout.alert_dialog_web_link, null)

                var dialogBuilder = AlertDialog.Builder(requireContext())
                    .setView(view)
                    .create()

                if (dialogBuilder.window  != null){
                    dialogBuilder.window!!.setBackgroundDrawable(ColorDrawable(0))
                }
                val inputTextET = view.findViewById<EditText>(R.id.web_link_edt)
                val addButton = view.findViewById<TextView>(R.id.add_button)
                val cancelButton = view.findViewById<TextView>(R.id.cancel_button)

                addButton
                    .setOnClickListener{
                        val inputText = inputTextET
                            .text
                            .toString().trim()
                        if (inputText.isEmpty()){
                            showToast(requireContext(),
                                "Enter Url")
                        }else if (!Patterns.WEB_URL.matcher(inputText).matches()){
                            showToast(requireContext(),
                                "Enter valid url")
                        }else{
                            linkTv
                                .setText(inputText)
                            dialogBuilder.dismiss()
                        }

                    }

                cancelButton.setOnClickListener{
                    dialogBuilder.dismiss()
                }
                dialogBuilder.show()
            }
    }

    private fun openWebLink(webLink:String) {
        try {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("www.youtube.com"))
            if (intent.resolveActivity(
                requireActivity()
                    .packageManager
            )!=null){
                startActivity(intent)
            }else{
                showToast(
                    requireContext(),
                    "No applicatoin availble to open this link"
                )
            }
        }catch (e:Exception){
            showToast(
                requireContext(),
                "Unable to open this please sure from web link is correctly"
            )
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