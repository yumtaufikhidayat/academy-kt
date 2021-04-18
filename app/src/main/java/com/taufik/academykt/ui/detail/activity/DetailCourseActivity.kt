package com.taufik.academykt.ui.detail.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.taufik.academykt.R
import com.taufik.academykt.data.CourseEntity
import com.taufik.academykt.databinding.ActivityDetailCourseBinding
import com.taufik.academykt.databinding.ContentDetailCourseBinding
import com.taufik.academykt.ui.academy.ViewModelFactory
import com.taufik.academykt.ui.detail.adapter.DetailCourseAdapter
import com.taufik.academykt.ui.detail.viewmodel.DetailCourseViewModel
import com.taufik.academykt.ui.reader.activity.CourseReaderActivity
import com.taufik.academykt.utils.DataDummy

class DetailCourseActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_COURSE = "com.taufik.academykt.ui.detail.activity.EXTRA_COURSE"
    }

    private lateinit var detailContentBinding: ContentDetailCourseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityDetailCourseBinding = ActivityDetailCourseBinding.inflate(layoutInflater)
        detailContentBinding = activityDetailCourseBinding.detailContent
        setContentView(activityDetailCourseBinding.root)

        setSupportActionBar(activityDetailCourseBinding.toolbar)

        initActionBar()

        setData()
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setData() {

        val adapter = DetailCourseAdapter()
        val extras = intent.extras
        if (extras != null) {
            val courseId = extras.getString(EXTRA_COURSE)
            if (courseId != null) {
                val factory = ViewModelFactory.getInstance(this)
                val viewModel = ViewModelProvider(this, factory)[DetailCourseViewModel::class.java]
                viewModel.setSelectedCourse(courseId)
                val modules = viewModel.getModules()
                adapter.setModules(modules)
                for (course in DataDummy.generateDummyCourses()) {
                    if (course.courseId == courseId) {
                        populateCourse(viewModel.getCourse())
                    }
                }
            }
        }

        with(detailContentBinding.rvModule) {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(this@DetailCourseActivity)
            setHasFixedSize(true)
            this.adapter = adapter
            val dividerItemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun populateCourse(courseEntity: CourseEntity) {
        detailContentBinding.apply {
            tvTitle.text = courseEntity.title
            tvDescription.text = courseEntity.description
            tvDate.text = resources.getString(R.string.deadline_date, courseEntity.deadline)

            Glide.with(this@DetailCourseActivity)
                .load(courseEntity.imagePath)
                .transform(RoundedCorners(20))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error))
                .into(imgPoster)

            btnStart.setOnClickListener {
                val intent = Intent(this@DetailCourseActivity, CourseReaderActivity::class.java).apply {
                    putExtra(CourseReaderActivity.EXTRA_COURSE_ID, courseEntity.courseId)
                }
                startActivity(intent)
            }
        }
    }
}