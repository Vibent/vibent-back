package com.vibent.vibentback.common.error.api;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vibent.vibentback.common.error.ErrorBodySerializer;
import com.vibent.vibentback.common.error.VibentError;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@JsonSerialize(using = ErrorBodySerializer.class)
public class ErrorBody {

    public ErrorBody(VibentError error){
        this.code = error.name();
        this.status = error.getStatus();
        this.message = error.getDefaultMessage();
    }

    private String code;
    private HttpStatus status;
    private String message;
}
