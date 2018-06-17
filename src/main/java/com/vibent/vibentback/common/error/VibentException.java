package com.vibent.vibentback.common.error;

import lombok.Getter;

@Getter
public class VibentException extends RuntimeException {
    private final VibentError error;

    public VibentException(VibentError error) {
        super(error.name().toLowerCase());
        this.error = error;
    }
}
