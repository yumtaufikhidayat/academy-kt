package com.taufik.academykt.ui.academy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.taufik.academykt.data.AcademyRepository
import com.taufik.academykt.data.source.local.entity.CourseEntity

class AcademyViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    fun getCourses(): LiveData<List<CourseEntity>> = academyRepository.getAllCourses()
}