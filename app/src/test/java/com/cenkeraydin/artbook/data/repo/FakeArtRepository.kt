package com.cenkeraydin.artbook.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cenkeraydin.artbook.data.model.ImageResponse
import com.cenkeraydin.artbook.data.repository.ArtRepository
import com.cenkeraydin.artbook.data.room.Art
import com.cenkeraydin.artbook.util.Resource

class FakeArtRepository : ArtRepository {
    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(arts)

    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshLiveData()
    }

    override suspend fun deleteArt(art: Art) {
       arts.remove(art)
        refreshLiveData()
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(), 0, 0))
    }

    private fun refreshLiveData() {
        artsLiveData.postValue(arts)
    }
}