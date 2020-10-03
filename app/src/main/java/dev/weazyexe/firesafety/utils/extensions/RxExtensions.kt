package dev.weazyexe.firesafety.utils.extensions

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

fun Unit.toCompletable(): Completable = Observable.just(this).ignoreElements()