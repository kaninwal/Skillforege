package com.example.skillforege

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class CategoryAdapter(private val categories: List<Category>, private val onClick: (Category) -> Unit = {}) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: View = view.findViewById(R.id.viewCategoryIcon)
        val name: TextView = view.findViewById(R.id.tvCategoryName)
        val count: TextView = view.findViewById(R.id.tvCourseCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.name.text = category.name
        holder.count.text = "${category.courseCount} courses"
        
        category.iconColor?.let {
            try {
                holder.icon.setBackgroundColor(Color.parseColor(it))
            } catch (e: Exception) {
                holder.icon.setBackgroundColor(Color.parseColor("#26A69A"))
            }
        }
        
        holder.itemView.setOnClickListener { onClick(category) }
    }

    override fun getItemCount() = categories.size
}

class CourseAdapter(private val courses: List<Course>, private val onClick: (Course) -> Unit) :
    RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val thumb: ImageView = view.findViewById(R.id.ivCourseThumb)
        val level: TextView = view.findViewById(R.id.tvCourseLevel)
        val title: TextView = view.findViewById(R.id.tvCourseTitle)
        val instructor: TextView = view.findViewById(R.id.tvInstructorName)
        val rating: TextView = view.findViewById(R.id.tvRating)
        val duration: TextView = view.findViewById(R.id.tvDuration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = courses[position]
        holder.title.text = course.title
        holder.level.text = course.level
        holder.instructor.text = course.instructorName
        holder.rating.text = course.rating.toString()
        holder.duration.text = course.duration
        
        if (!course.imageUrl.isNullOrEmpty()) {
            holder.thumb.load(course.imageUrl)
        }
        
        holder.itemView.setOnClickListener { onClick(course) }
    }

    override fun getItemCount() = courses.size
}

class LessonAdapter(private val lessons: List<Lesson>, private val onClick: (Lesson) -> Unit) :
    RecyclerView.Adapter<LessonAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvLessonTitle)
        val duration: TextView = view.findViewById(R.id.tvLessonDuration)
        val icon: ImageView = view.findViewById(R.id.ivLessonIcon)
        val freeTag: TextView = view.findViewById(R.id.tvFreeTag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lesson, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lesson = lessons[position]
        holder.title.text = lesson.title
        holder.duration.text = lesson.duration
        
        if (lesson.isPlaying) {
            holder.icon.setImageResource(android.R.drawable.ic_media_pause)
            holder.itemView.setBackgroundResource(R.drawable.bg_rect_teal_light)
        } else if (lesson.isLocked == true) {
            holder.icon.setImageResource(android.R.drawable.ic_lock_idle_lock)
            holder.itemView.alpha = 0.5f
        } else {
            holder.icon.setImageResource(android.R.drawable.ic_media_play)
            holder.itemView.alpha = 1.0f
        }
        
        holder.freeTag.visibility = if (lesson.isFree == true) View.VISIBLE else View.GONE
        
        holder.itemView.setOnClickListener { 
            if (lesson.isLocked != true) onClick(lesson)
        }
    }

    override fun getItemCount() = lessons.size
}