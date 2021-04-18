package com.taufik.academykt.ui.bookmark.viewmodel

import androidx.lifecycle.ViewModel
import com.taufik.academykt.data.CourseEntity
import com.taufik.academykt.data.source.AcademyRepository

class BookmarkViewModel(private val academyRepository: AcademyRepository) : ViewModel() {

    fun getBookmarks(): List<CourseEntity> = academyRepository.getBookmarkedCourses()
}