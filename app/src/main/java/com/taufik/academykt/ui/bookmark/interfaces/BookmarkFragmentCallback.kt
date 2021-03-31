package com.taufik.academykt.ui.bookmark.interfaces

import com.taufik.academykt.data.CourseEntity

interface BookmarkFragmentCallback {
    fun onShareClick(course: CourseEntity)
}
