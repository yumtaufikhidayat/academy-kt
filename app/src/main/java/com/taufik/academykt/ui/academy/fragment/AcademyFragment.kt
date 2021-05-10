package com.taufik.academykt.ui.academy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.academykt.databinding.FragmentAcademyBinding
import com.taufik.academykt.ui.academy.ViewModelFactory
import com.taufik.academykt.ui.academy.adapter.AcademyAdapter
import com.taufik.academykt.ui.academy.viewmodel.AcademyViewModel

class AcademyFragment : Fragment() {

    private lateinit var fragmentAcademyBinding: FragmentAcademyBinding

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
                viewModel.getCourses().observe(viewLifecycleOwner, {
                    progressBar.visibility = View.GONE
                    academyAdapter.setCourses(it)
                    academyAdapter.notifyDataSetChanged()
                })
            }

            with(fragmentAcademyBinding.rvAcademy) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = academyAdapter
            }
        }
    }
}