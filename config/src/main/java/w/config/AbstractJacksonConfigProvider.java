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

package w.config;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import w.config.transformer.AbstractTransformer;
import w.config.transformer.Transformer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author whilein
 */
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractJacksonConfigProvider implements JacksonConfigProvider {

    @Getter
    ObjectWriter objectWriter;

    @Getter
    ObjectReader objectReader;

    @Getter
    ObjectConverter objectConverter;

    private Config loadObject(final Map<?, ?> map) {
        val object = new ConfigImpl(new LinkedHashMap<>());
        loadObject(map, object);

        return object;
    }

    private void loadObject(final Map<?, ?> map, final Config object) {
        for (val entry : map.entrySet()) {
            val key = entry.getKey().toString();
            val value = entry.getValue();

            if (value instanceof Map) {
                loadObject((Map<?, ?>) value, object.createObject(key));
            } else {
                object.set(key, value);
            }
        }
    }

    @Override
    @SneakyThrows
    public void save(final @NotNull File file, final @NotNull Object object) {
        try (val fos = new FileOutputStream(file)) {
            objectWriter.writeValue(fos, object);
        }
    }

    @Override
    @SneakyThrows
    public void save(final @NotNull Path path, final @NotNull Object object) {
        try (val os = Files.newOutputStream(path)) {
            objectWriter.writeValue(os, object);
        }
    }

    @Override
    @SneakyThrows
    public void save(final @NotNull Writer writer, final @NotNull Object object) {
        objectWriter.writeValue(writer, object);
    }

    @Override
    @SneakyThrows
    public void save(final @NotNull OutputStream stream, final @NotNull Object object) {
        objectWriter.writeValue(stream, object);
    }

    @Override
    @SneakyThrows
    public @NotNull String saveAsString(final @NotNull Object object) {
        return objectWriter.writeValueAsString(object);
    }

    @Override
    @SneakyThrows
    public byte @NotNull [] saveAsBytes(final @NotNull Object object) {
        return objectWriter.writeValueAsBytes(object);
    }

    @Override
    @SneakyThrows
    public <E> @NotNull E load(final @NotNull File file, final @NotNull Class<E> type) {
        try (val fis = new FileInputStream(file)) {
            return objectReader.readValue(fis, type);
        }
    }

    @Override
    public <E> @NotNull E load(final @NotNull Object input, final @NotNull Class<E> type) {
        return objectConverter.convert(input, type);
    }

    @Override
    @SneakyThrows
    public <E> @NotNull E load(final @NotNull Path path, final @NotNull Class<E> type) {
        try (val is = Files.newInputStream(path)) {
            return objectReader.readValue(is, type);
        }
    }

    @Override
    @SneakyThrows
    public <E> @NotNull E load(final @NotNull Reader reader, final @NotNull Class<E> type) {
        return objectReader.readValue(reader, type);
    }

    @Override
    @SneakyThrows
    public <E> @NotNull E load(final @NotNull InputStream stream, final @NotNull Class<E> type) {
        return objectReader.readValue(stream, type);
    }

    @Override
    @SneakyThrows
    public <E> @NotNull E load(final @NotNull String input, final @NotNull Class<E> type) {
        return objectReader.readValue(input, type);
    }

    @Override
    @SneakyThrows
    public <E> @NotNull E load(final byte @NotNull [] input, final @NotNull Class<E> type) {
        return objectReader.readValue(input, type);
    }

    @Override
    @SneakyThrows
    public @NotNull Config parse(final @NotNull Path path) {
        try (val is = Files.newInputStream(path)) {
            return _parse(is);
        }
    }

    @Override
    public @NotNull Config newObject() {
        return new ConfigImpl(new LinkedHashMap<>());
    }

    @Override
    @SneakyThrows
    public @NotNull Config parse(final @NotNull File file) {
        try (val is = new FileInputStream(file)) {
            return _parse(is);
        }
    }

    @Override
    @SneakyThrows
    public @NotNull Config parse(final @NotNull String input) {
        return loadObject(objectReader.readValue(input, Map.class));
    }

    @Override
    @SneakyThrows
    public @NotNull Config parse(final byte @NotNull [] input) {
        return loadObject(objectReader.readValue(input, Map.class));
    }

    @Override
    @SneakyThrows
    public @NotNull Config parse(final @NotNull Reader reader) {
        return loadObject(objectReader.readValue(reader, Map.class));
    }

    @Override
    @SneakyThrows
    public @NotNull Config parse(final @NotNull InputStream is) {
        return _parse(is);
    }

    private Config _parse(final InputStream is) throws IOException {
        return loadObject(objectReader.readValue(is, Map.class));
    }

    private final class TransformAsImpl<T> extends AbstractTransformer<T> {

        private TransformAsImpl(final Class<T> type) {
            super(type);
        }

        @Override
        protected T doTransform(final Object o) {
            try {
                return objectConverter.convert(o, type);
            } catch (final Exception e) {
                return null;
            }
        }
    }

    private final class ConfigImpl extends AbstractMapConfig {

        private ConfigImpl(final Map<String, Object> map) {
            super(map);
        }

        @Override
        @SneakyThrows
        public String toString() {
            return objectWriter.writeValueAsString(map);
        }

        @Override
        public int hashCode() {
            return map.hashCode();
        }

        @Override
        public boolean equals(final Object obj) {
            return obj == this || obj instanceof ConfigImpl config && map.equals(config.map);
        }

        @Override
        protected Config createObject(final Map<String, Object> map) {
            return new ConfigImpl(map);
        }

        @Override
        public @NotNull <T> Transformer<T> transformAs(final @NotNull Class<T> type) {
            return new TransformAsImpl<>(type);
        }

        @Override
        @SneakyThrows
        public void writeTo(final @NotNull Writer writer) {
            objectWriter.writeValue(writer, map);
        }

        @Override
        @SneakyThrows
        public void writeTo(final @NotNull OutputStream os) {
            objectWriter.writeValue(os, map);
        }

        @Override
        public <T> T asType(final @NotNull Class<T> type) {
            return objectConverter.convert(map, type);
        }
    }
}
