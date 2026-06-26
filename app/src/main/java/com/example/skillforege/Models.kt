package com.example.skillforege

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiResponse(
    @SerializedName("categories") val categories: List<Category>? = emptyList()
) : Serializable

data class Category(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("iconColor") val iconColor: String? = null,
    @SerializedName("courseCount") val courseCount: Int? = 0,
    @SerializedName("courses") val courses: List<Course>? = emptyList()
) : Serializable

data class Course(
    @SerializedName("id") val id: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("subtitle") val subtitle: String? = null,
    @SerializedName("thumbnailUrl") val imageUrl: String? = null,
    @SerializedName("level") val level: String? = null,
    @SerializedName("durationHours") val durationHours: Double? = 0.0,
    @SerializedName("rating") val rating: Double? = 0.0,
    @SerializedName("instructor") val instructorObj: Instructor? = null,
    @SerializedName("description") val courseDescription: String? = null,
    @SerializedName("lessons") val lessons: List<Lesson>? = emptyList()
) : Serializable {
    val instructorName: String get() = instructorObj?.name ?: "Unknown Instructor"
    val duration: String get() = "${durationHours}h"
}

data class Instructor(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("avatarUrl") val avatarUrl: String? = null,
    @SerializedName("bio") val bio: String? = null
) : Serializable

data class Lesson(
    @SerializedName("id") val id: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("durationMinutes") val durationMinutes: Int? = 0,
    @SerializedName("isFree") val isFree: Boolean? = false,
    @SerializedName("videoUrl") val videoUrl: String? = null,
    @SerializedName("content") val content: String? = null,
    @SerializedName("isLocked") val isLocked: Boolean? = false,
    @SerializedName("isPlaying") var isPlaying: Boolean = false
) : Serializable {
    val duration: String get() = "${durationMinutes} min"
}