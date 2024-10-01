package com.training.hilt_roomdatabase.core_presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.training.hilt_roomdatabase.core_data.local.entity.NoteEntity
import com.training.hilt_roomdatabase.core_presentation.adapter.NoteAdapter
import com.training.hilt_roomdatabase.core_presentation.viewmodel.AllNotesViewModel
import com.training.hilt_roomdatabase.databinding.FragmentAllNotesBinding
import com.training.hilt_roomdatabase.extensions.navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllNotesFragment : Fragment() {
    private lateinit var binding:FragmentAllNotesBinding

    private val noteAdapter : NoteAdapter by lazy {
        NoteAdapter{
            var action = AllNotesFragmentDirections.actionAllNotesFragmentToUpdateNoteFragment(it)
            navigate(action)
        }
    }

     private val allNotesViewModel: AllNotesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllNotesBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMainRv()
        allNotesViewModel.getAllNotes()
        observeToStates()
        binding.addNoteBtn.setOnClickListener{
            navigate(AllNotesFragmentDirections.actionAllNotesFragmentToAddNoteFragment())
        }
    }

    private fun observeToStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            allNotesViewModel.notesState.collectLatest {
                checkItem(it)
            }
        }
    }

    private fun checkItem(
        notes:List<NoteEntity>
    ) {
        binding.apply {
            if (notes.isNotEmpty()){
                rvNoteList.visibility = View.VISIBLE
                tvEmptyText.visibility = View.GONE
                noteAdapter.differ.submitList(notes)
            }else{
                rvNoteList.visibility = View.GONE
                tvEmptyText.visibility = View.VISIBLE
            }
        }
    }

    private fun initMainRv() {
        binding.rvNoteList.apply {
            layoutManager = StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL)
            adapter = noteAdapter
        }
    }

}