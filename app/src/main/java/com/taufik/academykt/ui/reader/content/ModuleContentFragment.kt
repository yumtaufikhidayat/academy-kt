package com.taufik.academykt.ui.reader.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.taufik.academykt.data.source.local.entity.ModuleEntity
import com.taufik.academykt.databinding.FragmentModuleContentBinding
import com.taufik.academykt.ui.academy.ViewModelFactory
import com.taufik.academykt.ui.reader.viewmodel.CourseReaderViewModel
import com.taufik.academykt.vo.Status

class ModuleContentFragment : Fragment() {

    companion object {
        val TAG: String = ModuleContentFragment::class.java.simpleName
        fun newInstance(): ModuleContentFragment = ModuleContentFragment()
    }

    private lateinit var viewModel: CourseReaderViewModel
    private lateinit var fragmentModuleContentBinding: FragmentModuleContentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentModuleContentBinding = FragmentModuleContentBinding.inflate(inflater, container, false)
        return fragmentModuleContentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(requireActivity(), factory)[CourseReaderViewModel::class.java]

            fragmentModuleContentBinding.apply {
                progressBar.visibility = View.VISIBLE
                viewModel.selectedModule.observe(viewLifecycleOwner, { moduleEntity ->
                    if (moduleEntity != null) {
                        when (moduleEntity.status) {
                            Status.LOADING -> progressBar.visibility = View.VISIBLE
                            Status.SUCCESS -> if (moduleEntity.data != null) {
                                progressBar.visibility = View.GONE
                                if (moduleEntity.data.contentEntity != null) {
                                    populateWebView(moduleEntity.data)
                                }
                                setButtonNextPrevState(moduleEntity.data)
                                if (!moduleEntity.data.read) {
                                    viewModel.readContent(moduleEntity.data)
                                }
                            }
                            Status.ERROR -> {
                                progressBar.visibility = View.GONE
                                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }

                        btnPrev.setOnClickListener { viewModel.setPrevPage() }
                        btnNext.setOnClickListener { viewModel.setNextPage() }
                    }
                })
            }
        }
    }

    private fun populateWebView(module: ModuleEntity) {
        fragmentModuleContentBinding.apply {
            webView.loadData(module.contentEntity?.content ?: " ", "text/html", "UTF-8")
        }
    }

    private fun setButtonNextPrevState(module: ModuleEntity) {
        fragmentModuleContentBinding.apply {
            if (activity != null) {
                when (module.position) {
                    0 -> {
                        btnPrev.isEnabled = false
                        btnNext.isEnabled = true
                    }
                    viewModel.getModuleSize() - 1 -> {
                        btnPrev.isEnabled = true
                        btnNext.isEnabled = false
                    }
                    else -> {
                        btnPrev.isEnabled = true
                        btnNext.isEnabled = true
                    }
                }
            }
        }
    }
}