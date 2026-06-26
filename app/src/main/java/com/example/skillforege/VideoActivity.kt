package com.example.skillforege

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout

class VideoActivity : AppCompatActivity() {
    private var player: ExoPlayer? = null
    private lateinit var playerView: PlayerView
    private var currentLesson: Lesson? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        currentLesson = intent.getSerializableExtra("LESSON_DATA") as? Lesson
        val playlist = intent.getSerializableExtra("PLAYLIST_DATA") as? ArrayList<Lesson>

        playerView = findViewById(R.id.playerView)
        
        setupHeader()
        setupTabs()

        currentLesson?.let {
            updateLessonUI(it)
            initializePlayer(it)
        }

        if (playlist != null) {
            setupPlaylist(playlist, currentLesson?.id ?: "")
        }
    }

    private fun setupHeader() {
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }

    private fun setupTabs() {
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val rvLessons = findViewById<RecyclerView>(R.id.rvLessons)
        val tvNotes = findViewById<TextView>(R.id.tvNotes)
        val tvResources = findViewById<TextView>(R.id.tvResources)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        rvLessons.visibility = View.VISIBLE
                        tvNotes.visibility = View.GONE
                        tvResources.visibility = View.GONE
                    }
                    1 -> {
                        rvLessons.visibility = View.GONE
                        tvNotes.visibility = View.VISIBLE
                        tvResources.visibility = View.GONE
                        updateNotesContent()
                    }
                    2 -> {
                        rvLessons.visibility = View.GONE
                        tvNotes.visibility = View.GONE
                        tvResources.visibility = View.VISIBLE
                        updateResourcesContent()
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun updateLessonUI(lesson: Lesson) {
        findViewById<TextView>(R.id.tvLessonTitle).text = lesson.title
        findViewById<TextView>(R.id.tvLessonBreadcrumb).text = "LESSON ${lesson.id}"
        findViewById<TextView>(R.id.tvLessonDesc).text = lesson.content
    }

    private fun updateNotesContent() {
        val tvNotes = findViewById<TextView>(R.id.tvNotes)
        tvNotes.text = "Notes for ${currentLesson?.title}:\n\n- Key concepts of ${currentLesson?.title}\n- Code snippets and explanations\n- Practical examples."
    }

    private fun updateResourcesContent() {
        val tvResources = findViewById<TextView>(R.id.tvResources)
        tvResources.text = "Resources for ${currentLesson?.title}:\n\n- Lecture Slides (PDF)\n- Sample Code Repository\n- Recommended Reading links."
    }

    private fun initializePlayer(lesson: Lesson) {
        player?.release()
        player = ExoPlayer.Builder(this).build()
        playerView.player = player

        // Using a sample video since API might have dummy URLs
        val videoUrl = if (lesson.videoUrl?.startsWith("http") == true) {
            lesson.videoUrl
        } else {
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        }

        val mediaItem = MediaItem.fromUri(videoUrl!!)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.playWhenReady = true
    }

    private fun setupPlaylist(lessons: List<Lesson>, currentLessonId: String) {
        val rvLessons = findViewById<RecyclerView>(R.id.rvLessons)
        val displayLessons = lessons.map { 
            it.copy(isPlaying = it.id == currentLessonId) 
        }

        rvLessons.adapter = LessonAdapter(displayLessons) { selectedLesson ->
            currentLesson = selectedLesson
            updateLessonUI(selectedLesson)
            initializePlayer(selectedLesson)
            setupPlaylist(lessons, selectedLesson.id ?: "")
            
            // Reset tabs to Lessons
            findViewById<TabLayout>(R.id.tabLayout).getTabAt(0)?.select()
        }
        rvLessons.layoutManager = LinearLayoutManager(this)
    }

    override fun onStop() {
        super.onStop()
        player?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }
}