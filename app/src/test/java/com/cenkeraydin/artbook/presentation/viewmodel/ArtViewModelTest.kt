package com.cenkeraydin.artbook.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cenkeraydin.artbook.MainCoroutineRule
import com.cenkeraydin.artbook.data.repo.FakeArtRepository
import com.cenkeraydin.artbook.getOrAwaitValueTest
import com.cenkeraydin.artbook.presentation.viewModel.ArtViewModel
import com.cenkeraydin.artbook.util.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()




    private lateinit var viewModel: ArtViewModel

    @Before
    fun setUp() {
        viewModel = ArtViewModel(FakeArtRepository())
    }

    @Test
    fun ` insert art without year returns error `() {
        viewModel.makeArt("Mona Lisa", "Da Vinci", "")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun ` insert art without name returns error`() {
        viewModel.makeArt("", "Da Vinci", "1503")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun ` insert art without artistName returns error`() {
        viewModel.makeArt("Mona Lisa", "", "1503")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}