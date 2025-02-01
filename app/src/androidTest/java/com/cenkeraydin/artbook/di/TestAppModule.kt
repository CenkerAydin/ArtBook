package com.cenkeraydin.artbook.di

import android.content.Context
import androidx.fragment.app.FragmentFactory
import androidx.room.Room
import com.cenkeraydin.artbook.data.room.ArtDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun injectInMemoryRoom(@ApplicationContext context: Context) =
         Room.inMemoryDatabaseBuilder(context, ArtDB::class.java)
            .allowMainThreadQueries()
            .build()



}