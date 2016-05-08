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

import org.junit.Assert
import org.junit.Test
import java.util.*
import java.util.concurrent.ConcurrentHashMap


class ExtensionTests {

    @Test
    fun MutableMap_uniquePut() = sharedUniquePutTests("non-concurrent", HashMap<String, String>())


    @Test // doesn't test concurrency; impl doesn't affect atomicity, this just makes sure the code path is hit
    fun ConcurrentMap_uniquePut() = sharedUniquePutTests("concurrent", ConcurrentHashMap<String, String>())


    @Test
    fun mapToIntArray() {
        // to be passed into method under test: for compactness, just use an Int collection,
        // though this exact use case probably wouldn't make sense
        val input: Collection<Int> = listOf(2, 8 , -101, 500, 2)
        //because this particular list happens to be Collection<Int> we can use this convenience method
        val expected = input.toIntArray();
        val actual = input.mapToIntArray { it }

        Assert.assertArrayEquals("arrays not equal", expected, actual);
    }

    private fun sharedUniquePutTests(msg: String, map: MutableMap<String, String>) {
        val dupedKey = "dupedKey"
        val origValue = "origValue"
        map.uniquePut(dupedKey, origValue)
        // didn't throw an exception... so far so good
        map.uniquePut("someOtherKey", "whocares")
        // try non-unique key, expect exception
        try {
            map.uniquePut(dupedKey, "someOtherValue")
            Assert.fail("$msg -- uniquePut didn't throw an exception will duplicate key")

        } catch(e: Exception) {}

        Assert.assertEquals("original value was replaced", origValue, map[dupedKey])
    }
}