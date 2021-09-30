package com.taufik.academykt.ui.bookmark.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.taufik.academykt.R
import com.taufik.academykt.data.source.local.entity.CourseEntity
import com.taufik.academykt.databinding.FragmentBookmarkBinding
import com.taufik.academykt.ui.academy.ViewModelFactory
import com.taufik.academykt.ui.bookmark.adapter.BookmarkAdapter
import com.taufik.academykt.ui.bookmark.interfaces.BookmarkFragmentCallback
import com.taufik.academykt.ui.bookmark.viewmodel.BookmarkViewModel

class BookmarkFragment : Fragment(), BookmarkFragmentCallback {

    private lateinit var fragmentBookmarkBinding: FragmentBookmarkBinding
    private lateinit var viewModel: BookmarkViewModel
    private lateinit var bookmarkAdapter: BookmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentBookmarkBinding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return fragmentBookmarkBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
    }

    private fun setData() {
        fragmentBookmarkBinding.apply {
            itemTouchHelper.attachToRecyclerView(rvBookmark)
            if (activity != null) {

                val factory = ViewModelFactory.getInstance(requireActivity())
                viewModel = ViewModelProvider(this@BookmarkFragment, factory)[BookmarkViewModel::class.java]
                bookmarkAdapter = BookmarkAdapter(this@BookmarkFragment)

                progressBar.visibility = View.VISIBLE
                viewModel.getBookmarks().observe(viewLifecycleOwner, {
                    progressBar.visibility = View.GONE
                    bookmarkAdapter.submitList(it)
                })

                with(rvBookmark) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = bookmarkAdapter
                }
            }
        }
    }

    override fun onShareClick(course: CourseEntity) {
        if (activity != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(requireActivity())
                .setType(mimeType)
                .setChooserTitle("Bagikan aplikasi ini sekarang")
                .setText(resources.getString(R.string.share_text, course.title))
                .startChooser()
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val courseEntity = bookmarkAdapter.getSwipedData(swipedPosition)
                courseEntity?.let { viewModel.setBookmark(it) }
                val snackBar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.message_ok) { _ ->
                    courseEntity?.let { viewModel.setBookmark(it) }
                }
                snackBar.show()
            }
        }
    })
}