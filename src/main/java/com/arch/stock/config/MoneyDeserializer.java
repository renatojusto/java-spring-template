package com.arch.stock.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;

import java.io.IOException;
import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_HALF_UP;

public class MoneyDeserializer extends JsonDeserializer<BigDecimal> {

    private NumberDeserializers.BigDecimalDeserializer delegate = NumberDeserializers.BigDecimalDeserializer.instance;

    @Override
    public BigDecimal deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.getDecimalValue() == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal bd = delegate.deserialize(jsonParser, deserializationContext);
        return bd.setScale(8, ROUND_HALF_UP);
    }
}
