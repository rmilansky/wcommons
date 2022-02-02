/*
 *    Copyright 2022 Whilein
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

package w.util.hash;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;

/**
 * @author whilein
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class KeccakDigestFactory extends AbstractDigestFactory {

    int bits;

    public static @NotNull DigestFactory create(final int bits) {
        if (!BouncyCastle.isAvailable()) {
            return UnsupportedDigestFactory.getInstance();
        }

        if (bits != 224 && bits != 256 && bits != 288 && bits != 384 && bits != 512) {
            throw new IllegalArgumentException("Keccak algorithm does not support " + bits + " bit length");
        }

        return new KeccakDigestFactory(bits);
    }

    private static final class Factory {
        public static MessageDigest newDigestKeccak(final int bits) {
            return new Keccak.DigestKeccak(bits);
        }
    }

    @Override
    protected MessageDigest getDigest() {
        return Factory.newDigestKeccak(bits);
    }

}
