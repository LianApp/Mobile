package com.example.vkrapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vkrapplication.api.lessons.Lesson

class LessonsAdapter(private val lessonsList: MutableList<Lesson>)
    : RecyclerView.Adapter<LessonsAdapter.LessonsViewHolder>() {

    var onItemClick: ((Lesson) -> Unit)? = null

    class LessonsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView: TextView = itemView.findViewById(R.id.lesson_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lesson_card,parent,false)
        return LessonsViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonsViewHolder, position: Int) {
        val lesson = lessonsList[position]
        holder.textView.text = lesson.title

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(lesson)
        }
    }

    override fun getItemCount(): Int {
        return lessonsList.size
    }
}