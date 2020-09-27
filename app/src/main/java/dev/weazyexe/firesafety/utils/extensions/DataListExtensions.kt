package dev.weazyexe.firesafety.utils.extensions

import ru.surfstudio.android.datalistlimitoffset.domain.datalist.DataList
import ru.surfstudio.android.easyadapter.pagination.PaginationState

/**
 * Сливает пришедшие данные от репозитория и уже полученные данные если требуется
 * Сливать не требуется в случае, если имеющийся список пуст
 */
fun <T> DataList<T>?.mergeIfNeed(list: DataList<T>): DataList<T>? {
    return if (this?.isEmpty() == true) this else this?.merge(list)
}

/**
 * Получат нужный [PaginationState]
 */
fun <T> DataList<T>.getState(): PaginationState =
    if (canGetMore()) PaginationState.READY else PaginationState.COMPLETE