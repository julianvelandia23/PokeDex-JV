package com.julianvelandia.data.di

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.julianvelandia.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, NAME_DATABASE)
            .setQueryExecutor(Executors.newSingleThreadExecutor())
            .fallbackToDestructiveMigration()
            .addCallback(DatabaseMigrationCallback(app))
            .build()
    }

    private class DatabaseMigrationCallback(
        private val app: Application
    ) : RoomDatabase.Callback() {
        override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
            super.onDestructiveMigration(db)
            clearAppUserData(app)
        }
        private fun clearAppUserData(app: Application) {
            val activityManager = app.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityManager.clearApplicationUserData()

        }
    }

    @Provides
    fun providesHomePokemonDao(appDatabase: AppDatabase) = appDatabase.homePokemonDao()

    @Provides
    fun providesDetailPokemonDao(appDatabase: AppDatabase) = appDatabase.detailPokemonDao()

    companion object {
        private const val NAME_DATABASE = "room_database.db"
    }


}