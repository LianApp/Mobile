package com.example.vkrapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vkrapplication.api.teacher.CourseResponse

class CourseTeacherAdapter(private val coursesTeacherList: MutableList<CourseResponse>)
    : RecyclerView.Adapter<CourseTeacherAdapter.CoursesTeacherViewHolder>() {

    var onItemClick: ((CourseResponse) -> Unit)? = null

        class CoursesTeacherViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val imageView : TextView = itemView.findViewById(R.id.course_image)
            val textView : TextView = itemView.findViewById(R.id.course_title)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesTeacherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate((R.layout.course_card), parent, false)
        return CoursesTeacherViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoursesTeacherViewHolder, position: Int) {
        val course = coursesTeacherList[position]
        holder.imageView.text = course.icon
        holder.textView.text = course.title

        holder.textView.setOnClickListener{
            onItemClick?.invoke((course))
        }
    }

    override fun getItemCount(): Int {
        return coursesTeacherList.size
    }
}