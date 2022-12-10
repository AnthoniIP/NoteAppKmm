package com.ipsoft.noteappkmm.android.di

import android.app.Application
import com.ipsoft.noteappkmm.data.local.DatabaseDriverFactory
import com.ipsoft.noteappkmm.data.note.SqlDelightNoteDataSource
import com.ipsoft.noteappkmm.database.NoteDatabase
import com.ipsoft.noteappkmm.domain.note.NoteDataSource
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver = DatabaseDriverFactory(app).createDriver()

    @Provides
    @Singleton
    fun provideNoteDataSource(driver: SqlDriver): NoteDataSource =
        SqlDelightNoteDataSource(NoteDatabase(driver))
}
