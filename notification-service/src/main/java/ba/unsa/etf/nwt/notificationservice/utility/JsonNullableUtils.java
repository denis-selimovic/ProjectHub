package ba.unsa.etf.nwt.notificationservice.utility;

import org.openapitools.jackson.nullable.JsonNullable;
import java.util.function.Consumer;

public final class JsonNullableUtils {
    private JsonNullableUtils() {}

    public static <T> void changeIfPresent(JsonNullable<T> nullable, Consumer<T> consumer) {
        if (nullable.isPresent()) {
            consumer.accept(nullable.get());
        }
    }

    public static <T, Q> void changeIfPresent( JsonNullable<Q> nullable, Consumer<T>  consumer, T value) {
        if (nullable.isPresent()) {
            consumer.accept(value);
        }
    }
}