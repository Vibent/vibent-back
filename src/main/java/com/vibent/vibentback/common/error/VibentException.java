package com.vibent.vibentback.common.error;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VibentException extends RuntimeException {
    private final VibentError error;
    private String customMessage;
    private Exception originalException;

    public VibentException(VibentError error) {
        super(error.name().toLowerCase());
        this.error = error;
    }

    public VibentException(VibentError error, Exception originalException) {
        super(error.name().toLowerCase());
        this.error = error;
        this.originalException = originalException;
    }


    public VibentException(VibentError error, String customMessage) {
        super(error.name().toLowerCase());
        this.error = error;
        this.customMessage = customMessage;
    }
}
