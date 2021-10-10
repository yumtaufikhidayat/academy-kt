package com.taufik.academykt.ui.academy.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.academykt.databinding.FragmentAcademyBinding
import com.taufik.academykt.ui.academy.ViewModelFactory
import com.taufik.academykt.ui.academy.adapter.AcademyAdapter
import com.taufik.academykt.ui.academy.viewmodel.AcademyViewModel
import com.taufik.academykt.vo.Status

class AcademyFragment : Fragment() {

    private var _fragmentAcademyBinding: FragmentAcademyBinding? = null
    private val binding get() = _fragmentAcademyBinding

    companion object {
        const val TAG = "ACADEMY_FRAGMENT"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentAcademyBinding = FragmentAcademyBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[AcademyViewModel::class.java]
            val academyAdapter = AcademyAdapter()

            viewModel.getCourses().observe(this, { courses ->
                if (courses != null) {
                    when (courses.status) {
                        Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            binding?.progressBar?.visibility = View.GONE
                            Log.e(TAG, "setData: ${courses.data}")
                            academyAdapter.submitList(courses.data)
                        }
                        Status.ERROR -> {
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            with(binding?.rvAcademy) {
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = academyAdapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentAcademyBinding = null
    }
}