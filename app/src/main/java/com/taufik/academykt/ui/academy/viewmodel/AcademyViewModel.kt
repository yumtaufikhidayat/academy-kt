package com.taufik.academykt.ui.academy.viewmodel

import androidx.lifecycle.ViewModel
import com.taufik.academykt.data.CourseEntity
import com.taufik.academykt.utils.DataDummy

class AcademyViewModel : ViewModel() {

    fun getCourses(): List<CourseEntity> = DataDummy.generateDummyCourses()
}