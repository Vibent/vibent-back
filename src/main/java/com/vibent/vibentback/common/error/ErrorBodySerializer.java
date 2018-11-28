package com.vibent.vibentback.common.error;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.vibent.vibentback.common.error.api.ErrorBody;

import java.io.IOException;

public class ErrorBodySerializer extends StdSerializer<ErrorBody> {
    public ErrorBodySerializer() {
        super(ErrorBody.class);
    }

    public ErrorBodySerializer(Class t) {
        super(t);
    }

    @Override
    public void serialize(ErrorBody error, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("error");
        jsonGenerator.writeStartObject();

        jsonGenerator.writeFieldName("code");
        jsonGenerator.writeString(String.valueOf(error.getCode()));
        jsonGenerator.writeFieldName("status");
        jsonGenerator.writeString(String.valueOf(error.getStatus().value()));
        jsonGenerator.writeFieldName("message");
        jsonGenerator.writeString(error.getMessage());

        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
    }
}
