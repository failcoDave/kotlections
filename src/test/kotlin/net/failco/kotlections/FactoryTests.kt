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

class FactoryTests {
    private enum class TestEnum { ONE, TWO, THREE, FOUR, FIVE }

    @Test fun buildEnumMap()        =   sharedMapTests(buildEnumMap(TestEnum::class.java, { it.name }))

    @Test fun buildMutableEnumMap() =   sharedMapTests(buildMutableEnumMap(TestEnum::class.java, { it.name }))

    /** makes sure each of [TestEnum] has a value equal to it's [TestEnum.name], per our arbitrary test initializer */
    private fun sharedMapTests(actual: Map<TestEnum, String>) {
        Assert.assertEquals("unequal size", actual.size, TestEnum.values().size)
        TestEnum.values().forEach { expectedKey ->
            Assert.assertEquals("unexpected value", expectedKey.name, actual[expectedKey])
        }
    }
}

