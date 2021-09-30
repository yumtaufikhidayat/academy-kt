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

    private lateinit var fragmentAcademyBinding: FragmentAcademyBinding

    companion object {
        const val TAG = "ACADEMY_FRAGMENT"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentAcademyBinding = FragmentAcademyBinding.inflate(inflater, container, false)
        return fragmentAcademyBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
    }

    private fun setData() {
        if (activity != null) {

            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[AcademyViewModel::class.java]
            val academyAdapter = AcademyAdapter()

            fragmentAcademyBinding.apply {
                progressBar.visibility = View.VISIBLE
                viewModel.getCourses().observe(viewLifecycleOwner, { courses ->
                    if (courses != null) {
                        when (courses.status) {
                            Status.LOADING -> progressBar.visibility = View.VISIBLE
                            Status.SUCCESS -> {
                                progressBar.visibility = View.GONE
                                Log.e(TAG, "setData: ${courses.data}")
                                academyAdapter.submitList(courses.data)
                            }
                            Status.ERROR -> {
                                progressBar.visibility = View.GONE
                                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })

                with(rvAcademy) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = academyAdapter
                }
            }
        }
    }
}