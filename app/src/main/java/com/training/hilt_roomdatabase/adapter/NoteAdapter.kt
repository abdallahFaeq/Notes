package com.training.hilt_roomdatabase.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.training.hilt_roomdatabase.databinding.ItemNoteBinding
import com.training.hilt_roomdatabase.db.NoteEntity
import com.training.hilt_roomdatabase.ui.AddActivity
import com.training.hilt_roomdatabase.ui.UpdateActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteAdapter @Inject constructor():Adapter<NoteAdapter.NoteHolder>(){

    private var context:Context?=null
    inner class NoteHolder(var binding:ItemNoteBinding):ViewHolder(binding.root){
        fun bind(itemNote : NoteEntity){
            binding.apply {
                tvTitle.setText(itemNote.title)
                tvDesc.setText(itemNote.desc)

                root.setOnClickListener{
                    var intent = Intent(context,UpdateActivity::class.java)
                    intent.putExtra("note-id",itemNote.id)
                    context?.startActivity(intent)
                }
            }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<NoteEntity>(){
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem == newItem
        }

    }

    var differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        context = parent.context
        return NoteHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }
}