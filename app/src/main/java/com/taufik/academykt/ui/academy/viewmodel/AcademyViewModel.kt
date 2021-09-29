package com.taufik.academykt.ui.academy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.taufik.academykt.data.source.local.entity.CourseEntity
import com.taufik.academykt.data.AcademyRepository

class AcademyViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    fun getCourses(): LiveData<List<CourseEntity>> = academyRepository.getAllCourses()
}