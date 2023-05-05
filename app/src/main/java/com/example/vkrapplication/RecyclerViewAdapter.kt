package com.example.vkrapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val subjectTitles = arrayOf("Математика", "Физика", "Русский язык", "Технология")

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var subjectTitle : TextView

        init {
            subjectTitle = itemView.findViewById(R.id.subjectTitle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.subject_card, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.subjectTitle.text = subjectTitles [position]
    }

    override fun getItemCount(): Int {
        return  subjectTitles.size
    }
}