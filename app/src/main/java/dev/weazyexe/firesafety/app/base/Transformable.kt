package dev.weazyexe.firesafety.app.base

/**
 * Интерфейс объектов, способных к трансформации
 */
interface Transformable<T> {
    fun transform(): T
}