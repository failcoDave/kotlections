/*
 * Copyright 2016 David Hull
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.failco.kotlections

import java.util.concurrent.ConcurrentMap

/**
 * Attempts to atomically map the given [value] with the given [key].  If another item is already
 * present with the given [key], this throws an [IllegalArgumentException] and leaves this map unchanged.
 */
fun <K, V> ConcurrentMap<K, V>.uniquePut(key: K, value: V): V {
    if (this.putIfAbsent(key, value) != null)
        throw IllegalArgumentException("Key already exists: $key")
    return value;
}

/**
 * Attempts to map the given [value] with the given [key].  If another item is already
 * present with the given [key], this throws an [IllegalArgumentException] and leaves this map unchanged.
 */
fun <K, V> MutableMap<K, V>.uniquePut(key: K, value: V): V {
    if (this is ConcurrentMap) return this.uniquePut(key, value); // smart-cast to concurrent impl
    if (contains(key)) throw IllegalArgumentException("Key already exists: $key")
    put(key, value)
    return value
}

/** Uses the given [transformer] on each element in this collection to generate an [IntArray] */
inline fun <T> Collection<T>.mapToIntArray(transformer: (T) -> Int): IntArray {
    val ret = IntArray(this.size)
    forEachIndexed { idx, t -> ret[idx] = transformer(t) }
    return ret
}