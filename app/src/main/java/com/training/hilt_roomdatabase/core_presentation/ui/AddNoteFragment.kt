package com.training.hilt_roomdatabase.core_presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.training.hilt_roomdatabase.R
import com.training.hilt_roomdatabase.core_data.local.entity.NoteEntity
import com.training.hilt_roomdatabase.core_data.repository.NoteRepositoryImp
import com.training.hilt_roomdatabase.core_presentation.viewmodel.AddNoteViewModel
import com.training.hilt_roomdatabase.databinding.FragmentAddNoteBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddNoteFragment : Fragment() {
    private lateinit var binding : FragmentAddNoteBinding

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

        binding.apply {
            btnSave.setOnClickListener{
                var title = edtTitle.text.toString().trim()
                var desc = edtDesc.text.toString().trim()

                if (title.isNotEmpty() || desc.isNotEmpty()){
                    note = NoteEntity(
                        0,
                        title,
                        desc
                    )

                    note?.let {
                        Log.e("note",note.toString())
                        addNoteViewModel.insertNote(it)
                    }
                    findNavController().popBackStack()
                }else{
                    Snackbar.make(it,"title and description can't empty at the same task", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}