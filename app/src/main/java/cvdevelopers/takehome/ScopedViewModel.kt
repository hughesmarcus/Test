package cvdevelopers.takehome

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine scoped ViewModel
 *
 * ViewModel with CoroutineScope implemented, canceling all launched coroutines on [ViewModel.onCleared]
 *
 * @param[dispatcher] the default dispatcher used on this CoroutineScope. Optional, defaults to [Dispatchers.Main]
 */

open class ScopedViewModel(dispatcher: CoroutineDispatcher = Dispatchers.Main) : ViewModel(),
    CoroutineScope {
    private val scope = CoroutineScope(dispatcher + SupervisorJob())
    override val coroutineContext: CoroutineContext = scope.coroutineContext

    @ExperimentalCoroutinesApi @CallSuper override fun onCleared() {
        scope.cancel()
    }
}