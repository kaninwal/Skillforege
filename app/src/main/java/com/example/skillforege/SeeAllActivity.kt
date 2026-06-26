package com.example.skillforege

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SeeAllActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_all)

        val type = intent.getStringExtra("TYPE")
        val title = intent.getStringExtra("TITLE") ?: "See All"
        
        findViewById<TextView>(R.id.tvTitle).text = title
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }

        val rvSeeAll = findViewById<RecyclerView>(R.id.rvSeeAll)

        if (type == "CATEGORIES") {
            val categories = intent.getSerializableExtra("DATA") as? ArrayList<Category> ?: arrayListOf()
            rvSeeAll.layoutManager = GridLayoutManager(this, 2)
            rvSeeAll.adapter = CategoryAdapter(categories) { category ->
                // Handle category click if needed
            }
        } else if (type == "COURSES") {
            val courses = intent.getSerializableExtra("DATA") as? ArrayList<Course> ?: arrayListOf()
            rvSeeAll.layoutManager = LinearLayoutManager(this)
            rvSeeAll.adapter = CourseAdapter(courses) { course ->
                val intent = Intent(this, LessonsActivity::class.java)
                intent.putExtra("COURSE_DATA", course)
                startActivity(intent)
            }
        }
    }
}