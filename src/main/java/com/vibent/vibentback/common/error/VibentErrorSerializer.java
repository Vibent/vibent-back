package com.vibent.vibentback.common.error;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class VibentErrorSerializer extends StdSerializer<VibentError> {
    public VibentErrorSerializer() {
        super(VibentError.class);
    }

    public VibentErrorSerializer(Class t) {
        super(t);
    }

    @Override
    public void serialize(VibentError error, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("error");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("code");
        jsonGenerator.writeString(String.valueOf(error.name().toLowerCase()));
        jsonGenerator.writeFieldName("status");
        jsonGenerator.writeString(String.valueOf(error.getStatus().value()));
        jsonGenerator.writeFieldName("message");
        jsonGenerator.writeString(error.getMessage());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
    }
}
