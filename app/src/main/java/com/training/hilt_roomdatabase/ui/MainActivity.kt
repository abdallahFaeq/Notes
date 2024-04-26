package com.training.hilt_roomdatabase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.hilt_roomdatabase.R
import com.training.hilt_roomdatabase.adapter.NoteAdapter
import com.training.hilt_roomdatabase.databinding.ActivityMainBinding
import com.training.hilt_roomdatabase.db.NoteDao
import com.training.hilt_roomdatabase.db.NoteDatabase
import com.training.hilt_roomdatabase.repository.NoteRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    @Inject
    lateinit var noteAdapter : NoteAdapter

    @Inject
    lateinit var repository: NoteRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initMainRv()

        binding.btnAddNote.setOnClickListener{
            startActivity(Intent(this,AddActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        checkItem()
    }

    private fun checkItem() {
        binding.apply {
            if (repository.getAllNotes().isNotEmpty()){
                rvNoteList.visibility = View.VISIBLE
                tvEmptyText.visibility = View.GONE
                noteAdapter.differ.submitList(repository.getAllNotes())
            }else{
                rvNoteList.visibility = View.GONE
                tvEmptyText.visibility = View.VISIBLE
            }
        }
    }

    private fun initMainRv() {
        binding.rvNoteList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
            adapter = noteAdapter
        }
    }
}

/* this is note app
    in this app you can add note or update or delete from room database
    i used room database and DI(Hilt Dagger)
 */