package com.entrevista.appdepeliculas

import android.content.Context
import com.android.volley.toolbox.Volley
import com.entrevista.appdepeliculas.viewmodel.MainViewModel
import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun checkTest() = runBlocking {
        Assert.assertTrue(true)
    }

}