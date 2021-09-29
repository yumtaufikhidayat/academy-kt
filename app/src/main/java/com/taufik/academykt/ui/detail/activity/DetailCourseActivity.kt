package com.taufik.academykt.ui.detail.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.taufik.academykt.R
import com.taufik.academykt.data.source.local.entity.CourseEntity
import com.taufik.academykt.databinding.ActivityDetailCourseBinding
import com.taufik.academykt.databinding.ContentDetailCourseBinding
import com.taufik.academykt.ui.academy.ViewModelFactory
import com.taufik.academykt.ui.detail.adapter.DetailCourseAdapter
import com.taufik.academykt.ui.detail.viewmodel.DetailCourseViewModel
import com.taufik.academykt.ui.reader.activity.CourseReaderActivity
import com.taufik.academykt.vo.Status

class DetailCourseActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_COURSE = "com.taufik.academykt.ui.detail.activity.EXTRA_COURSE"
    }

    private lateinit var activityDetailCourseBinding: ActivityDetailCourseBinding
    private lateinit var detailContentBinding: ContentDetailCourseBinding
    private lateinit var viewModel: DetailCourseViewModel
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDetailCourseBinding = ActivityDetailCourseBinding.inflate(layoutInflater)
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

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailCourseViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val courseId = extras.getString(EXTRA_COURSE)
            if (courseId != null) {

                activityDetailCourseBinding.apply {
                    progressBar.visibility = View.VISIBLE

                    viewModel.setSelectedCourse(courseId)
                    viewModel.courseModule.observe(this@DetailCourseActivity, { courseWithModuleResource ->
                        if (courseWithModuleResource != null) {
                            when (courseWithModuleResource.status) {
                                Status.LOADING -> progressBar.visibility =
                                    View.VISIBLE
                                Status.SUCCESS -> if (courseWithModuleResource.data != null) {
                                    progressBar.visibility = View.GONE
//                                    content.visibility = View.VISIBLE
                                    adapter.setModules(courseWithModuleResource.data.mModules)
                                    adapter.notifyDataSetChanged()
                                    populateCourse(courseWithModuleResource.data.mCourse)
                                }
                                Status.ERROR -> {
                                    progressBar.visibility = View.GONE
                                    Toast.makeText(
                                        applicationContext,
                                        "Terjadi kesalahan",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    })
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        viewModel.courseModule.observe(this, { courseWithModule ->
            if (courseWithModule != null) {
                when (courseWithModule.status) {
                    Status.LOADING -> activityDetailCourseBinding.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> if (courseWithModule.data != null) {
                        activityDetailCourseBinding.progressBar.visibility = View.GONE
                        val state = courseWithModule.data.mCourse.bookmarked
                        setBookmarkState(state)
                    }
                    Status.ERROR -> {
                        activityDetailCourseBinding.progressBar.visibility = View.GONE
                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_bookmark) {
            viewModel.setBookmark()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setBookmarkState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_bookmark)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmarked_white)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmark_white)
        }
    }
}