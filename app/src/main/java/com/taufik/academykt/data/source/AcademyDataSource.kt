package com.taufik.academykt.data.source

import com.taufik.academykt.data.CourseEntity
import com.taufik.academykt.data.ModuleEntity

interface AcademyDataSource {

    fun getAllCourses(): ArrayList<CourseEntity>

    fun getBookmarkedCourses(): ArrayList<CourseEntity>

    fun getCourseWithModules(courseId: String): CourseEntity

    fun getAllModulesByCourse(courseId: String): ArrayList<ModuleEntity>

    fun getContent(courseId: String, moduleId: String): ModuleEntity
}