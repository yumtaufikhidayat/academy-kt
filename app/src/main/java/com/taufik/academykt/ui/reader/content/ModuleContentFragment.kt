package com.taufik.academykt.ui.reader.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.taufik.academykt.data.ModuleEntity
import com.taufik.academykt.databinding.FragmentModuleContentBinding
import com.taufik.academykt.ui.academy.ViewModelFactory
import com.taufik.academykt.ui.reader.viewmodel.CourseReaderViewModel

class ModuleContentFragment : Fragment() {

    companion object {
        val TAG: String = ModuleContentFragment::class.java.simpleName
        fun newInstance(): ModuleContentFragment = ModuleContentFragment()
    }

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
            val viewModel = ViewModelProvider(requireActivity(), factory)[CourseReaderViewModel::class.java]

            fragmentModuleContentBinding.apply {
                progressBar.visibility = View.VISIBLE
                viewModel.getSelectedModule().observe(viewLifecycleOwner, {
                    progressBar.visibility = View.GONE
                    if (it != null) {
                        populateWebView(it)
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
}