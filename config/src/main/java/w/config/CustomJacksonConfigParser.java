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

package w.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;

/**
 * @author whilein
 */
public final class CustomJacksonConfigParser extends AbstractJacksonConfigParser {

    private CustomJacksonConfigParser(final ObjectMapper objectMapper) {
        super(objectMapper);
    }

    public static @NotNull CustomJacksonConfigParser create(final @NotNull ObjectMapper objectMapper) {
        return new CustomJacksonConfigParser(objectMapper);
    }

}