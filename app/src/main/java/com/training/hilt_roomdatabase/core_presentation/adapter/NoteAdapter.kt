package com.training.hilt_roomdatabase.core_presentation.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.training.hilt_roomdatabase.databinding.ItemNoteBinding
import com.training.hilt_roomdatabase.core_data.local.entity.NoteEntity
import java.io.File


class NoteAdapter(
    var onItemClick:(Long)->Unit
):Adapter<NoteAdapter.NoteHolder>(){

    private var context:Context?=null
    inner class NoteHolder(var binding:ItemNoteBinding):ViewHolder(binding.root){
        fun bind(itemNote : NoteEntity){
            binding.apply {
                titleTv.setText(itemNote.title)
                subtitleTv.setText(itemNote.desc)
                dateTimeTv.setText(itemNote.dateTime)
                itemNote.color?.let {
                    cardView.setCardBackgroundColor(it)
                }

            if (itemNote.imagePath.isNotEmpty()){
                Glide.with(itemView)
                    .load(Uri.parse(itemNote.imagePath))
                    .into(imageItemImg)
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
        var item = differ.currentList[position]
        holder.bind(item)

        holder.itemView.setOnClickListener{
            onItemClick(item.id)
        }
    }
}