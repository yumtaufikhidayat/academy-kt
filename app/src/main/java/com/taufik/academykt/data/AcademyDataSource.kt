package com.taufik.academykt.data

import androidx.lifecycle.LiveData
import com.taufik.academykt.data.source.local.entity.CourseEntity
import com.taufik.academykt.data.source.local.entity.CourseWithModule
import com.taufik.academykt.data.source.local.entity.ModuleEntity
import com.taufik.academykt.vo.Resource

interface AcademyDataSource {

    fun getAllCourses(): LiveData<Resource<List<CourseEntity>>>

    fun getBookmarkedCourses(): LiveData<List<CourseEntity>>

    fun getCourseWithModules(courseId: String): LiveData<Resource<CourseWithModule>>

    fun getAllModulesByCourse(courseId: String): LiveData<Resource<List<ModuleEntity>>>

    fun getContent(moduleId: String): LiveData<Resource<ModuleEntity>>

    fun setCourseBookmark(course: CourseEntity, state: Boolean)

    fun setReadModule(module: ModuleEntity)
}