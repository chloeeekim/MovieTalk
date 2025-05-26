package chloe.movietalk.exception.director;

import chloe.movietalk.dto.common.ErrorReason;
import chloe.movietalk.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DirectorErrorCode implements BaseErrorCode {

    DIRECTOR_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "DIRECTOR_001", "존재하지 않는 감독입니다.");

    private Integer status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder()
                .reason(reason)
                .code(code)
                .status(status)
                .build();
    }
}
