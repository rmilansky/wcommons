/*
 *    Copyright 2021 Whilein
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package w.asm;

import lombok.experimental.UtilityClass;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @author whilein
 */
@UtilityClass
public class Asm {

    public byte @NotNull [] toByteArray(final @NotNull Class<?> type) throws IOException {
        val internalClassName = type.getName().replace('.', '/');

        val inputStream = type.getClassLoader().getResourceAsStream(internalClassName + ".class");

        try (inputStream) {
            if (inputStream == null) {
                throw new RuntimeException("Unexpected error occurred");
            }

            return inputStream.readAllBytes();
        }
    }

}