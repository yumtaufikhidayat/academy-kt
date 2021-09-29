package com.taufik.academykt.di

import android.content.Context
import com.taufik.academykt.data.AcademyRepository
import com.taufik.academykt.data.source.local.LocalDataSource
import com.taufik.academykt.data.source.local.room.AcademyDatabase
import com.taufik.academykt.data.source.remote.RemoteDataSource
import com.taufik.academykt.utils.AppExecutors
import com.taufik.academykt.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): AcademyRepository {
        val database = AcademyDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.academyDao())
        val appExecutors = AppExecutors()
        return AcademyRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}