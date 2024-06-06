package com.device.store.util;

import lombok.experimental.UtilityClass;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

@UtilityClass
public class JsonNullableUtils {

    public static <T> void applyPatch(Supplier<JsonNullable<T>> supplier, Consumer<T> consumer) {
        final JsonNullable<T> valueJsonNullable = supplier.get();

        if (valueJsonNullable == null) {
            return;
        }
        if (valueJsonNullable.isPresent()) {
            consumer.accept(valueJsonNullable.get());
        } else {
            consumer.accept(null);
        }
    }
}