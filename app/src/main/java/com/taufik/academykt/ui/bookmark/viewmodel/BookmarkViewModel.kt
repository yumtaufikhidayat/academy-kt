package com.taufik.academykt.ui.bookmark.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.taufik.academykt.data.AcademyRepository
import com.taufik.academykt.data.source.local.entity.CourseEntity

class BookmarkViewModel(private val academyRepository: AcademyRepository) : ViewModel() {

    fun getBookmarks(): LiveData<PagedList<CourseEntity>> = academyRepository.getBookmarkedCourses()

    fun setBookmark(courseEntity: CourseEntity) {
        val newState = !courseEntity.bookmarked
        academyRepository.setCourseBookmark(courseEntity, newState)
    }
}