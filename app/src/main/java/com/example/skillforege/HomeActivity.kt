package com.example.skillforege

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private val apiService by lazy { ApiService.create() }
    private var currentData: ApiResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        setupHeader()
        fetchData()
    }

    private fun setupHeader() {
        findViewById<ImageView>(R.id.ivNotification).setOnClickListener {
            Toast.makeText(this, "Notifications clicked", Toast.LENGTH_SHORT).show()
        }

        findViewById<ShapeableImageView>(R.id.ivProfile).setOnClickListener {
            Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
        }

        findViewById<TextView>(R.id.tvCategoriesSeeAll).setOnClickListener {
            currentData?.categories?.let { categories ->
                val intent = Intent(this, SeeAllActivity::class.java)
                intent.putExtra("TYPE", "CATEGORIES")
                intent.putExtra("TITLE", "All Categories")
                intent.putExtra("DATA", ArrayList(categories))
                startActivity(intent)
            }
        }

        findViewById<TextView>(R.id.tvPopularSeeAll).setOnClickListener {
            val allCourses = currentData?.categories?.flatMap { it.courses ?: emptyList() } ?: emptyList()
            if (allCourses.isNotEmpty()) {
                val intent = Intent(this, SeeAllActivity::class.java)
                intent.putExtra("TYPE", "COURSES")
                intent.putExtra("TITLE", "Popular Courses")
                intent.putExtra("DATA", ArrayList(allCourses))
                startActivity(intent)
            }
        }
    }

    private fun fetchData() {
        lifecycleScope.launch {
            try {
                val response = apiService.getData()
                currentData = response
                updateUI(response)
            } catch (e: Exception) {
                Toast.makeText(this@HomeActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }

    private fun updateUI(response: ApiResponse?) {
        if (response == null) return
        val categories = response.categories ?: emptyList()
        setupCategories(categories)
        
        // Aggregate all courses from all categories for the "Popular" section
        val allCourses = categories.flatMap { it.courses ?: emptyList() }
        setupPopularCourses(allCourses)
    }

    private fun setupCategories(categories: List<Category>) {
        val rvCategories = findViewById<RecyclerView>(R.id.rvCategories)
        rvCategories.adapter = CategoryAdapter(categories) { category ->
            // When category clicked, show all courses in that category
            val intent = Intent(this, SeeAllActivity::class.java)
            intent.putExtra("TYPE", "COURSES")
            intent.putExtra("TITLE", category.name)
            intent.putExtra("DATA", ArrayList(category.courses ?: emptyList()))
            startActivity(intent)
        }
        rvCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupPopularCourses(courses: List<Course>) {
        val rvPopularCourses = findViewById<RecyclerView>(R.id.rvPopularCourses)
        rvPopularCourses.layoutManager = LinearLayoutManager(this)
        rvPopularCourses.isNestedScrollingEnabled = false
        rvPopularCourses.adapter = CourseAdapter(courses) { course ->
            val intent = Intent(this, LessonsActivity::class.java)
            intent.putExtra("COURSE_DATA", course)
            startActivity(intent)
        }
    }
}