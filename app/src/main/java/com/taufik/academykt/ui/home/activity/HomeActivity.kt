package com.taufik.academykt.ui.home.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taufik.academykt.databinding.ActivityHomeBinding
import com.taufik.academykt.ui.home.adapter.SectionsPagerAdapter

class HomeActivity : AppCompatActivity() {

    private lateinit var activityHomeBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)

        initActionBar()

        setPagerAdapter()
    }

    private fun initActionBar() {
        supportActionBar?.elevation = 0f
    }

    private fun setPagerAdapter() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        activityHomeBinding.apply {
            viewPager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(activityHomeBinding.viewPager)
        }
    }
}