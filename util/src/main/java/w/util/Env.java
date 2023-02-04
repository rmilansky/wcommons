package w.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * @author whilein
 */
@UtilityClass
public class Env {

    public @NotNull String getString(final @NonNull String key, final @NonNull String defaultValue) {
        return getOptionalString(key).orElse(defaultValue);
    }

    public @NotNull Optional<@NotNull String> getOptionalString(final @NonNull String key) {
        return Optional.ofNullable(System.getenv(key));
    }

    public int getInt(final @NonNull String key, final  int defaultValue) {
        val value = System.getenv(key);

        if (value == null) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value);
        } catch (final Exception e) {
            return defaultValue;
        }
    }

}
