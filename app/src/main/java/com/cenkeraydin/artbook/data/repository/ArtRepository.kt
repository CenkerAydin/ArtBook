package com.cenkeraydin.artbook.data.repository

import androidx.lifecycle.LiveData
import com.cenkeraydin.artbook.data.model.ImageResponse
import com.cenkeraydin.artbook.data.room.Art
import com.cenkeraydin.artbook.util.Resource

interface ArtRepository {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun searchImage(imageString: String): Resource<ImageResponse>
}