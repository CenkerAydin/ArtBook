package com.cenkeraydin.artbook.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cenkeraydin.artbook.R
import com.cenkeraydin.artbook.data.repository.ArtRepository
import com.cenkeraydin.artbook.data.repository.ArtRepositoryImpl
import com.cenkeraydin.artbook.data.retrofit.RetrofitAPI
import com.cenkeraydin.artbook.data.room.ArtDB
import com.cenkeraydin.artbook.data.room.ArtDao
import com.cenkeraydin.artbook.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun roomModule(@ApplicationContext context: Context): ArtDB {
        return Room.databaseBuilder(
            context,
            ArtDB::class.java,
            "artBookDb"
        ).build()
    }

    @Singleton
    @Provides
    fun provideArtDao(artDB: ArtDB) = artDB.artDao()

    @Singleton
    @Provides
    fun retrofitModule(): RetrofitAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideRepo(dao: ArtDao, api: RetrofitAPI) = ArtRepositoryImpl(dao, api) as ArtRepository

    @Singleton
    @Provides
    fun glideModule(@ApplicationContext context: Context) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
    )
}