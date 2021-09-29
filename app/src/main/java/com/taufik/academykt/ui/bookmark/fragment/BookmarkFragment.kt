package com.taufik.academykt.ui.bookmark.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.academykt.R
import com.taufik.academykt.data.source.local.entity.CourseEntity
import com.taufik.academykt.databinding.FragmentBookmarkBinding
import com.taufik.academykt.ui.academy.ViewModelFactory
import com.taufik.academykt.ui.bookmark.adapter.BookmarkAdapter
import com.taufik.academykt.ui.bookmark.interfaces.BookmarkFragmentCallback
import com.taufik.academykt.ui.bookmark.viewmodel.BookmarkViewModel

class BookmarkFragment : Fragment(), BookmarkFragmentCallback {

    private lateinit var fragmentBookmarkBinding: FragmentBookmarkBinding

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
        if (activity != null) {

            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[BookmarkViewModel::class.java]
            val adapter = BookmarkAdapter(this)

            fragmentBookmarkBinding.apply {
                progressBar.visibility = View.VISIBLE
                viewModel.getBookmarks().observe(viewLifecycleOwner, {
                    progressBar.visibility = View.GONE
                    adapter.setCourses(it)
                    adapter.notifyDataSetChanged()
                })
            }

            with(fragmentBookmarkBinding.rvBookmark) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = adapter
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
}