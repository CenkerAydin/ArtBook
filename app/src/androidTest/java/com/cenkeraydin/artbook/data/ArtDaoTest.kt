package com.cenkeraydin.artbook.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.cenkeraydin.artbook.data.room.Art
import com.cenkeraydin.artbook.data.room.ArtDB
import com.cenkeraydin.artbook.data.room.ArtDao
import com.cenkeraydin.artbook.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var artDB : ArtDB

    private lateinit var dao : ArtDao

    @Before
    fun setup(){
        hiltRule.inject()
        dao = artDB.artDao()
    }

    @Test
    fun insertArtTest() = runTest {
        val exampleArt= Art("Mona Lisa", "Da Vinci", 1503, "www.google.com", 1)
        dao.insertArt(exampleArt)

        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).contains(exampleArt)
    }

    @Test
    fun deleteArtTest() = runTest {
        val exampleArt= Art("Mona Lisa", "Da Vinci", 1503, "www.google.com", 1)
        dao.insertArt(exampleArt)
        dao.deleteArt(exampleArt)

        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).doesNotContain(exampleArt)
    }

    @After
    fun tearDown(){
        artDB.close()
    }

}