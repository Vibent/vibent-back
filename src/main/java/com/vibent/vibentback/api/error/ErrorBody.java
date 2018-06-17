package com.vibent.vibentback.api.error;

import lombok.Data;

@Data
public class ErrorBody {
    private String code;
    private int status;
    private String message;
}
