package com.taufik.academykt.ui.bookmark.viewmodel

import androidx.lifecycle.ViewModel
import com.taufik.academykt.data.CourseEntity
import com.taufik.academykt.utils.DataDummy

class BookmarkViewModel : ViewModel() {

    fun getBookmarks(): List<CourseEntity> = DataDummy.generateDummyCourses()
}