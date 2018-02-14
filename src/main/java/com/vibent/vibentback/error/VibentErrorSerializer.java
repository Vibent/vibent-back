package com.vibent.vibentback.error;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class VibentErrorSerializer extends StdSerializer {
    public VibentErrorSerializer() {
        super(VibentError.class);
    }

    public VibentErrorSerializer(Class t) {
        super(t);
    }

    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        VibentError error = (VibentError) o;
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("code");
        jsonGenerator.writeString(error.getCode());
        jsonGenerator.writeFieldName("status");
        jsonGenerator.writeString(String.valueOf(error.getStatus().value()));
        jsonGenerator.writeFieldName("message");
        jsonGenerator.writeString(error.getMessage());
        jsonGenerator.writeEndObject();
    }
}
