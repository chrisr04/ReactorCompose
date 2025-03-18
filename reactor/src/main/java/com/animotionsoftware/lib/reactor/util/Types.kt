package com.animotionsoftware.lib.reactor.util

import com.animotionsoftware.lib.reactor.viewmodel.ReactorState
import kotlinx.coroutines.flow.StateFlow

/**
 * A type alias for an event handler.
 */
typealias EventHandler = () -> Unit

/**
 * A type alias for an event handler with arguments of type [T].
 *
 * @param T The type of the argument accepted by the event handler.
 */
typealias EventHandlerWithArgs<T> = (T) -> Unit

/**
 * A type alias for a StateFlow that holds a [ReactorState] with a value of type [T].
 *
 * @param T The type of state contained within the ReactorState.
 */
typealias ReactorFlow<T> = StateFlow<ReactorState<T>>