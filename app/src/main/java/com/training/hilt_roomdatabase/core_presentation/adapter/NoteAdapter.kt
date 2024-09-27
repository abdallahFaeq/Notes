package com.training.hilt_roomdatabase.core_presentation.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.training.hilt_roomdatabase.databinding.ItemNoteBinding
import com.training.hilt_roomdatabase.core_data.local.entity.NoteEntity


class NoteAdapter (
     private var onItemClick:(id:Long) -> Unit
):Adapter<NoteAdapter.NoteHolder>(){
    inner class NoteHolder(var binding:ItemNoteBinding):ViewHolder(binding.root){
        fun bind(itemNote : NoteEntity){
            binding.apply {
                tvTitle.setText(itemNote.title)
                tvDesc.setText(itemNote.desc)

                root.setOnClickListener{
                    // add higher order function that navigate and load data to update fragment when click on item

//                    var intent = Intent(context, UpdateActivity::class.java)
//                    intent.putExtra("note-id",itemNote.id)
//                    context?.startActivity(intent)
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
        var noteItem = differ.currentList.get(position)
        holder.bind(noteItem)

        holder.itemView.setOnClickListener{
            onItemClick(noteItem.id)
        }
    }
}