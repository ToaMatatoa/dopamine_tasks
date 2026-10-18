package com.matatoa.dopaminetasks.core.di

import android.content.Context
import androidx.room.Room
import com.matatoa.dopaminetasks.core.database.DopamineTaskDatabase
import com.matatoa.dopaminetasks.core.database.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): DopamineTaskDatabase = Room.databaseBuilder(
        context,
        DopamineTaskDatabase::class.java,
        "dopamine_db",
    ).build()

    @Provides
    fun provideTaskDao(db: DopamineTaskDatabase): TaskDao = db.taskDao()
}
