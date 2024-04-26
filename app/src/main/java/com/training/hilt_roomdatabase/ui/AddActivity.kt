package com.training.hilt_roomdatabase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.training.hilt_roomdatabase.R
import com.training.hilt_roomdatabase.databinding.ActivityAddBinding
import com.training.hilt_roomdatabase.db.NoteEntity
import com.training.hilt_roomdatabase.repository.NoteRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddBinding

    @Inject
    lateinit var repository: NoteRepository

    private lateinit var note:NoteEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

                    repository.insertNote(note)
                    finish()
                }else{
                    Snackbar.make(it,"title and description can't empty at the same task",Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}