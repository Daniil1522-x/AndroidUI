package com.example.androidui.di

import android.content.Context
import androidx.room.Room
import com.example.androidui.data.local.AppDatabase
import com.example.androidui.data.local.AppDatabaseMigrations
import com.example.androidui.data.local.AppDetailsDao
import com.example.androidui.data.network.AppApiService
import com.example.androidui.data.network.RetrofitClient
import com.example.androidui.data.repository.AppRepositoryImpl
import com.example.androidui.domain.repository.AppRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindAppRepository(
        impl: AppRepositoryImpl
    ): AppRepository

    companion object {

        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context
        ): AppDatabase =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME
            )
                .addMigrations(AppDatabaseMigrations.MIGRATION_1_2)
                .build()

        @Provides
        @Singleton
        fun provideDao(db: AppDatabase): AppDetailsDao =
            db.appDetailsDao()

        @Provides
        @Singleton
        fun provideApi(): AppApiService =
            RetrofitClient.apiService
    }
}