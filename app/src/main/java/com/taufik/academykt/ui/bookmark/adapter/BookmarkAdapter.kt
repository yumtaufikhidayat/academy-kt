package com.taufik.academykt.ui.bookmark.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.taufik.academykt.R
import com.taufik.academykt.data.source.local.entity.CourseEntity
import com.taufik.academykt.databinding.ItemsBookmarkBinding
import com.taufik.academykt.ui.bookmark.interfaces.BookmarkFragmentCallback
import com.taufik.academykt.ui.detail.activity.DetailCourseActivity

class BookmarkAdapter(
    private val callback: BookmarkFragmentCallback
) : PagedListAdapter<CourseEntity, BookmarkAdapter.CourseViewHolder>(DIFF_CALLBACK) {

    private val listCourses = ArrayList<CourseEntity>()

    fun getSwipedData(swipedPosition: Int): CourseEntity? = getItem(swipedPosition)

    inner class CourseViewHolder(private val itemsBookmarkBinding: ItemsBookmarkBinding)
        : RecyclerView.ViewHolder(itemsBookmarkBinding.root){
        fun bind(course: CourseEntity) {
            with(itemsBookmarkBinding) {
                tvItemTitle.text = course.title
                tvItemDate.text = itemView.resources.getString(R.string.deadline_date, course.deadline)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailCourseActivity::class.java)
                    intent.putExtra(DetailCourseActivity.EXTRA_COURSE, course.courseId)
                    itemView.context.startActivity(intent)
                }

                imgShare.setOnClickListener {
                    callback.onShareClick(course)
                }

                Glide.with(itemView.context)
                    .load(course.imagePath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(imgPoster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val itemsBookmarkBinding = ItemsBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(itemsBookmarkBinding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
//        val course = listCourses[position]
//        holder.bind(course)
        val course = getItem(position)
        if (course != null) {
            holder.bind(course)
        }
    }

    override fun getItemCount(): Int = listCourses.size

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CourseEntity>() {
            override fun areItemsTheSame(oldItem: CourseEntity, newItem: CourseEntity): Boolean {
                return oldItem.courseId == newItem.courseId
            }

            override fun areContentsTheSame(oldItem: CourseEntity, newItem: CourseEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}