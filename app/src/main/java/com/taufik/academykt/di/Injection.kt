package com.taufik.academykt.di

import android.content.Context
import com.taufik.academykt.data.AcademyRepository
import com.taufik.academykt.data.source.remote.RemoteDataSource
import com.taufik.academykt.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): AcademyRepository {
        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        return AcademyRepository.getInstance(remoteDataSource)
    }
}