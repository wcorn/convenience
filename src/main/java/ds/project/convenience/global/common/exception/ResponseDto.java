package ds.project.convenience.global.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseDto {
    private final String code;
    private final String message;

    public static ResponseDto of(ErrorCode code) {
        return new ResponseDto(code.getCode(), code.getMessage());
    }

    public static ResponseDto of(ErrorCode code, Exception e) {
        return new ResponseDto(code.getCode(), code.getMessage(e));
    }

    public static ResponseDto of(ErrorCode code, String message) {
        return new ResponseDto(code.getCode(), code.getMessage(message));
    }
}
