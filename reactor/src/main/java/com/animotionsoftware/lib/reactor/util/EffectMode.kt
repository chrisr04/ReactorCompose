package com.animotionsoftware.lib.reactor.util

/**
 * Represents the mode in which effects are executed within the `ReactorEffect`.
 */
sealed interface EffectMode {
    /**
     * The default effect mode, where effects are executed as usual.
     */
    data object Default : EffectMode

    /**
     * The startable effect mode, it's ideal for initial data loading.
     *
     * This mode provides an `onStart` method within the `ReactorEffect`, ensuring that
     * the method is executed only once until the screen is disposed.
     */
    data object Startable : EffectMode
}