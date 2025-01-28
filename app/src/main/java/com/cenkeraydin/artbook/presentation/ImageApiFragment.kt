package com.cenkeraydin.artbook.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.cenkeraydin.artbook.databinding.FragmentImageApiBinding
import com.cenkeraydin.artbook.presentation.adapter.ImageRecyclerAdapter
import com.cenkeraydin.artbook.presentation.viewModel.ArtViewModel
import com.cenkeraydin.artbook.util.Status
import com.cenkeraydin.artbook.util.Util.textChanges
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ImageApiFragment @Inject constructor(
    private val imageRecyclerAdapter: ImageRecyclerAdapter
) : Fragment() {

    private lateinit var binding: FragmentImageApiBinding
    private lateinit var viewModel: ArtViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageApiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]

        var job: Job? = null

        lifecycleScope.launch {
            binding.searchEt.textChanges()
                .debounce(1000) // 1 saniye bekle
                .collectLatest { query ->
                    if (!query.isNullOrEmpty()) {
                        viewModel.searchImage(query.toString())
                    }
                }
        }

        binding.imageRv.adapter = imageRecyclerAdapter
        binding.imageRv.layoutManager = GridLayoutManager(requireContext(), 3)

        imageRecyclerAdapter.setOnItemClickListener {
            viewModel.setSelectedImage(it)
            findNavController().popBackStack()
        }

        viewModel.imageResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult -> imageResult.previewURL }
                    imageRecyclerAdapter.images = urls ?: listOf()
                    binding.progressBar.visibility = View.GONE
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_LONG)
                        .show()
                    binding.progressBar.visibility = View.GONE

                }

                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE

                }
            }
        }
    }

}