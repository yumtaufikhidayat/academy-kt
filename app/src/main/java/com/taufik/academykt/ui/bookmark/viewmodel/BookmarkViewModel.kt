package com.taufik.academykt.ui.bookmark.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.taufik.academykt.data.source.local.entity.CourseEntity
import com.taufik.academykt.data.AcademyRepository

class BookmarkViewModel(private val academyRepository: AcademyRepository) : ViewModel() {

    fun getBookmarks(): LiveData<List<CourseEntity>> = academyRepository.getBookmarkedCourses()
}