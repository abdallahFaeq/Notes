package com.training.hilt_roomdatabase.core_presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.hilt_roomdatabase.core_presentation.adapter.NoteAdapter
import com.training.hilt_roomdatabase.databinding.ActivityMainBinding
import com.training.hilt_roomdatabase.core_data.repository.NoteRepositoryImp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

/* this is note app
    in this app you can add note or update or delete from database
    i used room database and DI(Hilt Dagger)
 */