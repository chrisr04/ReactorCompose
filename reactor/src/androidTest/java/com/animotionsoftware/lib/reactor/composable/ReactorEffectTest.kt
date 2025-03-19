package com.animotionsoftware.lib.reactor.composable

import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import com.animotionsoftware.lib.reactor.util.EffectMode
import com.animotionsoftware.lib.reactor.viewmodel.ReactorEvent
import com.animotionsoftware.lib.reactor.viewmodel.ReactorViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals

class ReactorEffectTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenReactorEffectIsCalled_thenTheEffectIsInvoked() {
        val mockedViewModel = CounterViewModel()
        var state = mockedViewModel.state

        composeTestRule.setContent {
            ReactorEffect(mockedViewModel.flow) {
                state = it
            }

            mockedViewModel.onEvent(CounterEvent.IncrementEvent)
        }

        assertEquals(1, state)
    }

    @Test
    fun whenManyStatesAreEmitted_thenTheOnStartIsInvokedOnlyOnce() {
        val mockedViewModel = CounterViewModel()
        var state = mockedViewModel.state
        var onStartCalls = 0

        composeTestRule.setContent {
            ReactorEffect(
                mockedViewModel.flow,
                mode = EffectMode.Startable
            ) {
                state = it
                onStart { onStartCalls++ }
            }

            mockedViewModel.onEvent(CounterEvent.IncrementEvent)
            mockedViewModel.onEvent(CounterEvent.IncrementEvent)
            mockedViewModel.onEvent(CounterEvent.IncrementEvent)
            mockedViewModel.onEvent(CounterEvent.IncrementEvent)
            mockedViewModel.onEvent(CounterEvent.IncrementEvent)
        }

        assertEquals(5, state)
        assertEquals(1, onStartCalls)
    }

    @Test
    fun whenConfigurationChanged_thenTheOnStartIsInvokedOnlyOnce() {
        val mockedViewModel = CounterViewModel()
        val restoreTester = StateRestorationTester(composeTestRule)
        var onStartCalls = 0

        restoreTester.setContent {
            ReactorEffect(
                mockedViewModel.flow,
                mode = EffectMode.Startable
            ) {
                onStart { onStartCalls++ }
            }
        }

        restoreTester.emulateSavedInstanceStateRestore()

        assertEquals(1, onStartCalls)
    }
}

class CounterViewModel() : ReactorViewModel<CounterEvent, Int>(0) {
    init {
        on<CounterEvent.IncrementEvent>(::onIncrementEvent)
    }

    private fun onIncrementEvent() {
        emit(state + 1)
    }
}

sealed interface CounterEvent : ReactorEvent {
    data object IncrementEvent : CounterEvent
}