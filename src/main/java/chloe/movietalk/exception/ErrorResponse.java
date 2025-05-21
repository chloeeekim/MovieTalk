package chloe.movietalk.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private final boolean success = false;
    private final int status;
    private final String code;
    private final String reason;
    private final LocalDateTime timestamp;
    private final String path;

    public ErrorResponse(int status, String code, String reason, LocalDateTime timestamp, String path) {
        this.status = status;
        this.code = code;
        this.reason = reason;
        this.timestamp = timestamp;
        this.path = path;
    }

    public ErrorResponse(ErrorReason errorReason, String path) {
        this.status = errorReason.getStatus();
        this.code = errorReason.getCode();
        this.reason = errorReason.getReason();
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }
}
