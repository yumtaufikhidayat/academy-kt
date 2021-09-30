package com.taufik.academykt.ui.academy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.taufik.academykt.data.AcademyRepository
import com.taufik.academykt.data.source.local.entity.CourseEntity
import com.taufik.academykt.vo.Resource

class AcademyViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    fun getCourses(): LiveData<Resource<PagedList<CourseEntity>>> = academyRepository.getAllCourses()
}