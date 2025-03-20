package com.animotionsoftware.lib.reactor.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.animotionsoftware.lib.reactor.util.ReactorFlow
import com.animotionsoftware.lib.reactor.viewmodel.ReactorState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


/**
 * Collects values from this [ReactorFlow] and represents its latest value via [State] in a
 * lifecycle-aware manner.
 *
 * **Note:** It's similar to `collectAsStateWithLifecycle()`.
 *
 * **Usage:**
 * ```
 *  val viewModel = viewModel<FeatureViewModel>()
 *  val state by viewModel.flow.collectAsReactorStateWithLifecycle()
 *  Text(text = state)
 * ```
 *
 * Warning: [Lifecycle.State.INITIALIZED] is not allowed in this API. Passing it as a
 * parameter will throw an [IllegalArgumentException].
 *
 * @param lifecycleOwner [LifecycleOwner] whose `lifecycle` is used to restart collecting `this`
 * flow.
 * @param minActiveState [Lifecycle.State] in which the upstream flow gets collected. The
 * collection will stop if the lifecycle falls below that state, and will restart if it's in that
 * state again.
 * @param context [CoroutineContext] to use for collecting.
 */
@Composable
@Suppress("StateFlowValueCalledInComposition")
fun <T> ReactorFlow<T>.collectAsReactorStateWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
): State<T> = collectAsReactorStateWithLifecycle(
    initialValue = this.value,
    lifecycle = lifecycleOwner.lifecycle,
    minActiveState = minActiveState,
    context = context
)

@Composable
internal fun <T> Flow<ReactorState<T>>.collectAsReactorStateWithLifecycle(
    initialValue: ReactorState<T>,
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
): State<T> {
    return produceState(initialValue.state, this, lifecycle, minActiveState, context) {
        lifecycle.repeatOnLifecycle(minActiveState) {
            if (context == EmptyCoroutineContext) {
                this@collectAsReactorStateWithLifecycle.collect {
                    this@produceState.value = it.state
                }
            } else withContext(context) {
                this@collectAsReactorStateWithLifecycle.collect {
                    this@produceState.value = it.state
                }
            }
        }
    }
}