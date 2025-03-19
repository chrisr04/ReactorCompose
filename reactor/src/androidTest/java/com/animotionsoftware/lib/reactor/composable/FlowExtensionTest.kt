package com.animotionsoftware.lib.reactor.composable

import org.junit.Rule
import androidx.compose.ui.test.junit4.createComposeRule
import com.animotionsoftware.lib.reactor.viewmodel.ReactorEvent
import com.animotionsoftware.lib.reactor.viewmodel.ReactorViewModelMock
import org.junit.Test
import androidx.compose.runtime.getValue
import org.junit.Assert.assertEquals

class FlowExtensionTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenExtensionIsCalled_thenStateIsCollected() {
        val mockedViewModel = ReactorViewModelMock<ReactorEvent, Int>(7)
        var stateCollected = 0

        composeTestRule.setContent {
            val state by mockedViewModel.flow.collectAsReactorStateWithLifecycle()
            stateCollected = state
        }

        assertEquals(7, stateCollected)
    }
}