package com.example.skillforege

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class LessonsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lessons)

        val course = intent.getSerializableExtra("COURSE_DATA") as? Course

        if (course != null) {
            findViewById<TextView>(R.id.tvCourseHeaderTitle).text = course.title
            findViewById<TextView>(R.id.tvCourseTitleDetail).text = course.title
            findViewById<TextView>(R.id.tvInstructorName).text = course.instructorName
            findViewById<TextView>(R.id.tvCourseRating).text = course.rating.toString()
            findViewById<TextView>(R.id.tvCourseDuration).text = course.duration
            findViewById<TextView>(R.id.tvCourseLevel).text = course.level
            
            val lessonList = course.lessons ?: emptyList()
            findViewById<TextView>(R.id.tvLessonSummary).text = "${lessonList.size} lessons • ${course.duration}"
            
            setupLessonsList(lessonList)
        }

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.btnFollow).setOnClickListener {
            val tvFollow = it as TextView
            if (tvFollow.text == "Follow") {
                tvFollow.text = "Following"
                Toast.makeText(this, "You are now following this instructor", Toast.LENGTH_SHORT).show()
            } else {
                tvFollow.text = "Follow"
            }
        }

        findViewById<MaterialButton>(R.id.btnEnroll).setOnClickListener {
            Toast.makeText(this, "Successfully enrolled in ${course?.title ?: "course"}", Toast.LENGTH_LONG).show()
            it.isEnabled = false
            (it as MaterialButton).text = "Enrolled"
        }
    }

    private fun setupLessonsList(lessons: List<Lesson>) {
        val rvLessons = findViewById<RecyclerView>(R.id.rvLessons)
        rvLessons.adapter = LessonAdapter(lessons) { lesson ->
            val intent = Intent(this, VideoActivity::class.java)
            intent.putExtra("LESSON_DATA", lesson)
            intent.putExtra("PLAYLIST_DATA", ArrayList(lessons))
            startActivity(intent)
        }
        rvLessons.layoutManager = LinearLayoutManager(this)
    }
}