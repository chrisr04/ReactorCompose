package com.animotionsoftware.lib.reactor.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.animotionsoftware.lib.reactor.util.EffectMode
import com.animotionsoftware.lib.reactor.util.EffectMode.*
import com.animotionsoftware.lib.reactor.util.ReactorFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.launch

/**
 * This composable observes changes in the provided [flow] and executes the [effect] function
 * whenever a new state is emitted. If a [reactWhen] predicate is provided, the function will be
 * called only when the predicate returns true for a given state.
 *
 * **Usage:**
 * ```
 *  ReactorEffect(flow){ state ->
 *
 *      // This is called when a new state is emitted
 *
 *  }
 * ```
 *
 * ```
 *  ReactorEffect(
 *      flow,
 *      reactWhen = { it.isLoading }
 *  ){
 *      // This is called when a new state is emitted
 *      // and the isLoading property is true
 *  }
 * ```
 *
 * @param T The type of state being observed.
 * @param flow The [ReactorFlow] that emits state updates.
 * @param mode The [EffectMode] for the effect.
 * @param reactWhen An optional condition to determine if the effect should execute for a given state.
 * @param effect The suspend function that executes when the effect is triggered.
 */
@Composable
@NonRestartableComposable
fun <T> ReactorEffect(
    flow: ReactorFlow<T>,
    mode: Default = Default,
    reactWhen: ((T) -> Boolean)? = null,
    effect: suspend (T) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var lastStateId by rememberSaveable { mutableIntStateOf(flow.value.id) }

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        coroutineScope.launch(Dispatchers.Main.immediate) {
            flow.filter {
                val isNewState = lastStateId != it.id
                if (isNewState) lastStateId = it.id
                isNewState && (reactWhen == null || reactWhen(it.state))
            }.collect {
                effect(it.state)
            }
        }
    }
}

/**
 * This composable observes changes in the provided [flow] and executes the [effect] function
 * whenever a new state is emitted. If a [reactWhen] predicate is provided, the function will be
 * called only when the predicate returns true for a given state.
 *
 * When [mode] is [Startable], it allows the `onStart` logic to be called only once until the screen is disposed.
 *
 * ```
 *  ReactorEffect(
 *      flow,
 *      mode = EffectMode.Startable
 *  ){ state ->
 *
 *      // This is called when a new state is emitted
 *
 *      onStart {
 *          // This is called only once
 *      }
 *  }
 * ```
 *
 * @param T The type of state being observed.
 * @param flow The [ReactorFlow] that emits state updates.
 * @param mode The [EffectMode] for the effect.
 * @param reactWhen An optional condition to determine if the effect should execute for a given state.
 * @param effect The suspend function that executes when the effect is triggered.
 */
@Composable
@NonRestartableComposable
fun <T> ReactorEffect(
    flow: ReactorFlow<T>,
    mode: Startable,
    reactWhen: ((T) -> Boolean)? = null,
    effect: suspend StartableEffectScope.(T) -> StartableEffectResult,
) {
    val effectScope = remember { StartableEffectScope() }
    val coroutineScope = rememberCoroutineScope()

    var isStarted by rememberSaveable { mutableStateOf(false) }
    var lastStateId by rememberSaveable { mutableIntStateOf(flow.value.id) }

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        coroutineScope.launch(Dispatchers.Main.immediate) {
            flow.onSubscription {
                if (isStarted) return@onSubscription
                effectScope.effect(flow.value.state).start()
                isStarted = true
            }.filter {
                val isNewState = lastStateId != it.id
                if (isNewState) lastStateId = it.id
                isNewState && (reactWhen == null || reactWhen(it.state))
            }.collect {
                effectScope.effect(it.state)
            }
        }
    }
}

class StartableEffectScope {
    inline fun onStart(
        crossinline onStartEffect: () -> Unit,
    ): StartableEffectResult = object : StartableEffectResult {
        override fun start() {
            onStartEffect()
        }
    }
}

interface StartableEffectResult {
    fun start()
}