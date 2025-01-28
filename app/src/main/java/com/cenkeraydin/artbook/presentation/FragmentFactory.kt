package com.cenkeraydin.artbook.presentation

import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.cenkeraydin.artbook.data.room.Art
import com.cenkeraydin.artbook.presentation.adapter.ArtRecyclerAdapter
import com.cenkeraydin.artbook.presentation.adapter.ImageRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class FragmentFactory @Inject constructor(
    private val artRecyclerAdapter: ArtRecyclerAdapter,
    private val glide: RequestManager,
    private val imageRecyclerAdapter: ImageRecyclerAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String) =
        when(className){
            ArtFragment::class.java.name -> ArtFragment(artRecyclerAdapter)
            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide)
            ImageApiFragment::class.java.name -> ImageApiFragment(imageRecyclerAdapter)
            else -> super.instantiate(classLoader, className)
        }
}