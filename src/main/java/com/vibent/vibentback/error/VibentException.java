package com.vibent.vibentback.error;

import lombok.Getter;

@Getter
public class VibentException extends RuntimeException {
    private final VibentError error;

    public VibentException(VibentError error) {
        super(error.getCode());
        this.error = error;
    }
}
