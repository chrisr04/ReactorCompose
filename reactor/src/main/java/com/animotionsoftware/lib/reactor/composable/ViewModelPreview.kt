package com.animotionsoftware.lib.reactor.composable

import androidx.compose.runtime.Composable
import com.animotionsoftware.lib.reactor.viewmodel.ReactorEvent
import com.animotionsoftware.lib.reactor.viewmodel.ReactorViewModel
import com.animotionsoftware.lib.reactor.viewmodel.ReactorViewModelMock

/**
 * Creates an instance of a [ReactorViewModel] for use in Jetpack Compose previews.
 *
 * This function returns a mock implementation of [ReactorViewModel] with the given initial state.
 * It's useful for providing a ViewModel instance in a preview environment without requiring
 * actual business logic.
 *
 * **Usage:**
 * ```
 * @Preview
 * @Composable
 * fun MyScreenPreview() {
 *     val viewModel = viewModelPreview<FeatureEvent, FeatureState>(
 *         FeatureState(
 *             value1 = "Preview value"
 *         )
 *     )
 *     MyScreen(
 *         flow = viewModel.flow,
 *         onEvent = {},
 *     )
 * }
 * ```
 *
 * @param Event The type of events handled by the ViewModel.
 * @param State The type representing the state of the ViewModel.
 * @param state The initial state to be used in the mock ViewModel.
 * @return A mock instance of [ReactorViewModel] initialized with the given state.
 */
@Composable
fun <Event : ReactorEvent, State> viewModelPreview(state: State): ReactorViewModel<Event, State> {
    return ReactorViewModelMock<Event, State>(state)
}