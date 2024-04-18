package ds.project.convenience.global.common.exception;

public class ErrorResponse extends ResponseDto {

    private ErrorResponse(ErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
    }

    private ErrorResponse(ErrorCode errorCode, Exception e) {
        super(errorCode.getCode(), errorCode.getMessage(e));
    }

    private ErrorResponse(ErrorCode errorCode, String message) {
        super(errorCode.getCode(), errorCode.getMessage(message));
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse of(ErrorCode errorCode, Exception e) {
        return new ErrorResponse(errorCode, e);
    }

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return new ErrorResponse(errorCode, message);
    }
}
