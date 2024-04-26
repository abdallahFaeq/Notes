package com.training.hilt_roomdatabase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.training.hilt_roomdatabase.R
import com.training.hilt_roomdatabase.databinding.ActivityUpdateBinding
import com.training.hilt_roomdatabase.db.NoteEntity
import com.training.hilt_roomdatabase.repository.NoteRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UpdateActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUpdateBinding

    @Inject
    lateinit var repository:NoteRepository

    private lateinit var noteUpdated:NoteEntity

    private var noteId : Long = 0
    private var defaultTitle :String = ""
    private var defaultDescription : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            noteId = it.getLong("note-id")
        }

        var note = repository.getNote(noteId)
        defaultTitle = note.title
        defaultDescription = note.desc

        binding.apply {
            edtTitle.setText(defaultTitle)
            edtDesc.setText(defaultDescription)

            btnSave.setOnClickListener{
                if(edtTitle.text.toString().trim().isNotEmpty() || edtDesc.text.toString().trim().isNotEmpty()){
                    noteUpdated = NoteEntity(
                        noteId,
                        edtTitle.text.toString().trim(),
                        edtDesc.text.toString().trim()
                    )

                    if (noteUpdated == note){
                        Toast.makeText(this@UpdateActivity,"data not changed",Toast.LENGTH_SHORT).show()
                    }else{
                        repository.updateNote(noteUpdated)
                    }
                }else{
                    Toast.makeText(this@UpdateActivity,"title and description can't be empty",Toast.LENGTH_SHORT).show()
                }
                finish()
            }

            btnDelete.setOnClickListener{
                repository.deleteNote(note)
                finish()
            }

        }

    }
}