/*
 *    Copyright 2023 Whilein
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

package w.crypto;

import lombok.experimental.UtilityClass;

/**
 * @author whilein
 */
@UtilityClass
public class BouncyCastle {

    private final boolean AVAILABLE;

    public boolean isAvailable() {
        return AVAILABLE;
    }

    static {
        boolean available;

        try {
            Class.forName("org.bouncycastle.LICENSE");

            available = true;
        } catch (final ClassNotFoundException e) {
            available = false;
        }

        AVAILABLE = available;
    }

}
