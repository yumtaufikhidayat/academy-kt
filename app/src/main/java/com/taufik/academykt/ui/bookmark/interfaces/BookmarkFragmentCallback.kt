package com.taufik.academykt.ui.bookmark.interfaces

import com.taufik.academykt.data.source.local.entity.CourseEntity

interface BookmarkFragmentCallback {
    fun onShareClick(course: CourseEntity)
}
