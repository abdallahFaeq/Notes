package com.training.hilt_roomdatabase.core_presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.training.hilt_roomdatabase.core_data.local.entity.NoteEntity
import com.training.hilt_roomdatabase.core_presentation.viewmodel.UpdateNoteViewModel
import com.training.hilt_roomdatabase.databinding.FragmentUpdateNoteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdateNoteFragment : Fragment() {
    private lateinit var binding:FragmentUpdateNoteBinding

    private val updateNoteViewModel:UpdateNoteViewModel by viewModels()
    private lateinit var noteUpdated: NoteEntity

    val args:UpdateNoteFragmentArgs by navArgs()

    private var noteId : Long = 0
    private var defaultTitle :String = ""
    private var defaultDescription : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteId = args.noteId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateNoteBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }
    var note:NoteEntity?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        updateNoteViewModel.getNote(noteId)
        observeNoteState()

        binding.apply {
            btnSave.setOnClickListener{
                if(edtTitle.text.toString().trim().isNotEmpty() || edtDesc.text.toString().trim().isNotEmpty()){
                    noteUpdated = NoteEntity(
                        noteId,
                        edtTitle.text.toString().trim(),
                        edtDesc.text.toString().trim()
                    )

                    if (noteUpdated == note){
                        Toast.makeText(context,"data not changed", Toast.LENGTH_SHORT).show()
                    }else{
                        updateNoteViewModel.updateNote(noteUpdated)
                    }
                }else{
                    Toast.makeText(context,"title and description can't be empty",
                        Toast.LENGTH_SHORT).show()
                }
                findNavController().popBackStack()
            }

            btnDelete.setOnClickListener{
                note?.let {
                    updateNoteViewModel.deleteNote(it)
                }

                findNavController().popBackStack()
            }

        }
    }

    private fun observeNoteState() {
        viewLifecycleOwner.lifecycleScope.launch {
            updateNoteViewModel.noteState.collectLatest {
                it?.let {
                    note = it
                    defaultTitle = it.title
                    defaultDescription = it.desc
                }

                binding.apply {
                    edtTitle.setText(defaultTitle)
                    edtDesc.setText(defaultDescription)
                }
            }
        }
    }

}