package dev.weazyexe.firesafety.ui.base

/**
 * Состояние экрана
 */
sealed class ScreenState<T> {

    data class Error<T>(val message: String): ScreenState<T>()
    data class Success<T>(val data: T): ScreenState<T>()

    sealed class Loading<T>: ScreenState<T>() {
        class MainLoading<T>: Loading<T>()
        class TransparentLoading<T>: Loading<T>()
        class SwipeRefreshLoading<T>: Loading<T>()
    }
}