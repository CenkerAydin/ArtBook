package com.cenkeraydin.artbook.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cenkeraydin.artbook.data.model.ImageResponse
import com.cenkeraydin.artbook.data.repository.ArtRepository
import com.cenkeraydin.artbook.data.room.Art
import com.cenkeraydin.artbook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ArtViewModel @Inject constructor(
    private val repository: ArtRepository
): ViewModel() {
    val artList = repository.getArt()

    private val _selectedImage = MutableLiveData<String>()
    val selectedImageUrl : LiveData<String>
        get() =_selectedImage

    private val _imageResponse = MutableLiveData<Resource<ImageResponse>>()
    val imageResponse: LiveData<Resource<ImageResponse>> get() = _imageResponse


    private var _insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage : LiveData<Resource<Art>>
        get() = _insertArtMsg

    fun resetInsertArtMsg() {
        _insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url : String) {
        _selectedImage.postValue(url)
    }

    fun deleteArt(art: Art) = viewModelScope.launch {
        repository.deleteArt(art)
    }

    private fun insertArt(art: Art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    fun makeArt(name : String, artistName : String, year : String) {
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty() ) {
            _insertArtMsg.postValue(Resource.error("Enter name, artist, year", null))
            return
        }
        val yearInt = try {
            year.toInt()
        } catch (e: Exception) {
            _insertArtMsg.postValue(Resource.error("Year should be number",null))
            return
        }

        val art = Art(name, artistName, yearInt,_selectedImage.value?: "")
        insertArt(art)
        setSelectedImage("")
        _insertArtMsg.postValue(Resource.success(art))
    }

    fun searchImage(imageString: String) {
        viewModelScope.launch {
            _imageResponse.value = Resource.loading(null)
            val response = repository.searchImage(imageString)
            _imageResponse.value = response
        }
    }
}

