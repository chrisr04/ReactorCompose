package com.animotionsoftware.lib.reactor.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.animotionsoftware.lib.reactor.util.EventHandler
import com.animotionsoftware.lib.reactor.util.EventHandlerWithArgs
import com.animotionsoftware.lib.reactor.util.ReactorFlow
import com.animotionsoftware.lib.reactor.util.IdGenerator
import com.animotionsoftware.lib.reactor.util.cast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * A base ViewModel implementation that integrates event handling and state management.
 *
 * This class provides a mechanism for handling events and updating state using Kotlin's StateFlow.
 *
 * **Usage:**
 * ```
 *  class FeatureViewModel() : ReactorViewModel<FeatureEvent, FeatureState>(FeatureState()){
 *      ...
 *  }
 * ```
 *
 * @param Event The type of events that this ViewModel handles.
 * @param State The type representing the state of this ViewModel.
 * @property initialState The initial state of the ViewModel.
 */
abstract class ReactorViewModel<Event : ReactorEvent, State>(initialState: State) : ViewModel() {

    protected val events = HashMap<String, EventHandler>()
    protected val eventsWithArgs = HashMap<String, EventHandlerWithArgs<ReactorEvent>>()

    private val idGenerator = IdGenerator()
    private val mutableFlow = MutableStateFlow(ReactorState(0, initialState))

    /** A read-only state flow representing the current state of the ViewModel. */
    val flow: ReactorFlow<State> = mutableFlow.asStateFlow()

    /** Provides easy access to the current state. */
    val state get() = flow.value.state

    /**
     * Registers an event handler for a specific event type.
     *
     * **Usage:**
     * ```
     *  init {
     *    on<MyEvent>(::onMyEvent)
     *  }
     *
     *  private fun onMyEvent() {
     *      ...
     *  }
     * ```
     *
     * @param T The event type.
     * @param handler The handler to be executed when the event occurs.
     */
    protected inline fun <reified T : Event> on(noinline handler: EventHandler) {
        events[T::class.java.name] = handler
    }

    /**
     * Registers an event handler that accepts arguments for a specific event type.
     *
     * **Usage:**
     * ```
     *  init {
     *    on<MyEvent>(::onMyEvent)
     *  }
     *
     *  private fun onMyEvent(event: MyEvent) {
     *      ...
     *  }
     * ```
     *
     * @param T The event type.
     * @param handler The handler to be executed when the event occurs.
     */
    protected inline fun <reified T : Event> on(noinline handler: EventHandlerWithArgs<T>) {
        eventsWithArgs[T::class.java.name] = cast<EventHandlerWithArgs<ReactorEvent>>(handler)
    }

    /**
     * Handles an incoming event by invoking the corresponding event handler. If the event is not registered
     * via [on], this method will not execute any event handler in your ViewModel.
     *
     * **Note:** You can override this method for specific features, but you will need to handle all events.
     *
     * **Usage:**
     * ```
     * val viewModel = viewModel<MyViewModel>()
     * MyScreen(
     *     flow = viewModel.flow,
     *     onEvent = viewModel::onEvent,
     * )
     * ```
     * **Override:**
     * ```
     * override fun <T : FeatureEvent> onEvent(event: T) {
     *     when(event){
     *         is MyEvent -> {}
     *         ...
     *     }
     * }
     * ```
     *
     * @param T The type of event being handled.
     * @param event The event instance to process.
     */
    open fun <T : Event> onEvent(event: T) {
        val eventName = event::class.java.name
        events[eventName]?.invoke() ?: eventsWithArgs[eventName]?.invoke(event)
    }

    /**
     * Updates the state of the ViewModel and notifies observers.
     *
     * **Usage:**
     * ```
     * emit(state.copy(isLoading = true))
     * ```
     *
     * @param newState The new state to be emitted.
     *
     */
    protected fun emit(newState: State) {
        mutableFlow.value = ReactorState(idGenerator.nextId(), newState)
    }

    /**
     * Clears event handlers when the ViewModel is destroyed.
     */
    @CallSuper
    override fun onCleared() {
        events.clear()
        eventsWithArgs.clear()
    }
}