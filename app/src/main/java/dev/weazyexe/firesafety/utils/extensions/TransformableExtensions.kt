package dev.weazyexe.firesafety.utils.extensions

import dev.weazyexe.firesafety.app.base.Transformable

/**
 * Экстеншн для трансформирования всех элементов коллекции
 */
fun <R, T: Transformable<R>> List<T>.transform(): List<R> {
    return map { it.transform() }
}