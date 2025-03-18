package com.animotionsoftware.lib.reactor.viewmodel

/**
 * A data class representing a state within the Reactor architecture.
 *
 * This class associates a unique identifier with a state value to track changes.
 * The equality check is based solely on the state value, ignoring the identifier.
 *
 * @param T The type of the state value.
 * @property id A unique identifier for the state instance.
 * @property state The actual state value.
 */
data class ReactorState<T>(val id: Int, val state: T) {

    /**
     * Compares this instance with another object for equality.
     * Two instances are considered equal if their state values are equal,
     * regardless of their unique identifiers.
     *
     * @param other The object to compare with.
     * @return `true` if the state values are equal, `false` otherwise.
     */
    override fun equals(other: Any?): Boolean = other is ReactorState<*> && state == other.state

    /**
     * Computes the hash code for this instance based on the state value.
     *
     * @return The hash code derived from the state value.
     */
    override fun hashCode(): Int = state.hashCode()
}
