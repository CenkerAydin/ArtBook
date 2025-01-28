package com.cenkeraydin.artbook.data.repository

import androidx.lifecycle.LiveData
import com.cenkeraydin.artbook.data.model.ImageResponse
import com.cenkeraydin.artbook.data.retrofit.RetrofitAPI
import com.cenkeraydin.artbook.data.room.Art
import com.cenkeraydin.artbook.data.room.ArtDao
import com.cenkeraydin.artbook.util.Resource
import javax.inject.Inject

class ArtRepositoryImpl @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitAPI: RetrofitAPI
):ArtRepository {

    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitAPI.imageSearch(imageString)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            }else{
                Resource.error("Error", null)
            }

    }catch (e:Exception){
        Resource.error("No data", null)
    }
    }
}