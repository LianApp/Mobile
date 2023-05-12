package com.example.vkrapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vkrapplication.api.student.CourseResponse

class CourseAdapter(private val coursesList:MutableList<CourseResponse>)
    : RecyclerView.Adapter<CourseAdapter.CoursesViewHolder>() {

    var onItemClick: ((CourseResponse) -> Unit)? = null

    class CoursesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView : TextView = itemView.findViewById(R.id.course_image)
        val textView : TextView = itemView.findViewById(R.id.course_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate((R.layout.course_card),parent,false)
        return  CoursesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) {
        val course = coursesList[position]
        holder.imageView.text = course.icon
        holder.textView.text = course.title

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(course)
        }
    }

    override fun getItemCount(): Int {
        return coursesList.size
    }
}