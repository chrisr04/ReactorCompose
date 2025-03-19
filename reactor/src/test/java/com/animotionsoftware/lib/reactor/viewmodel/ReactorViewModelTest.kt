package com.animotionsoftware.lib.reactor.viewmodel

import com.animotionsoftware.lib.reactor.viewmodel.CounterEvent.*
import org.junit.Test
import org.junit.Assert.assertEquals

class ReactorViewModelTest {

    private val viewModel = CounterViewModel()

    @Test
    fun whenViewModelIsCreated_thenInitialStateIsZero() {
        val firstState = viewModel.state
        assertEquals(0, firstState)
    }

    @Test
    fun whenOnEventIsCalled_thenNewStateIsEmitted() {
        viewModel.onEvent(IncrementEvent)
        val newState = viewModel.state

        assertEquals(1, newState)
    }

    @Test
    fun whenOnEventIsCalledAndEventIsNotRegistered_thenStateIsNotChanged() {
        viewModel.onEvent(DecrementEvent)
        val newState = viewModel.state

        assertEquals(0, newState)
    }
}

class CounterViewModel() : ReactorViewModel<CounterEvent, Int>(0) {
    init {
        on<IncrementEvent>(::onIncrementEvent)
        on<SetValueEvent>(::onSetValueEvent)
    }

    private fun onIncrementEvent() {
        emit(state + 1)
    }

    private fun onSetValueEvent(event: SetValueEvent) {
        emit(event.value)
    }
}

sealed interface CounterEvent : ReactorEvent {
    data object IncrementEvent : CounterEvent
    data object DecrementEvent : CounterEvent
    data class SetValueEvent(val value: Int) : CounterEvent
}

