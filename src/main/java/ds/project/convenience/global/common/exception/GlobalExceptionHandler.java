package ds.project.convenience.global.common.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {


    // 직접 정의한 에러
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(final CustomException e) {
        log.error("handleCustomException: {}", e.getErrorCode().toString());
        final ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
        return ResponseEntity
            .status(e.getErrorCode().getStatus())
            .body(errorResponse);
    }

    // 지원하지 않는 HttpRequestMethod
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
        final HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException: {}", e.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
        return ResponseEntity
            .status(ErrorCode.METHOD_NOT_ALLOWED.getStatus())
            .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(final Exception e) {
        log.error("handleException: {}", e.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity
            .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
            .body(errorResponse);
    }

    //validation exception 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> processValidationError(MethodArgumentNotValidException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.BAD_REQUEST,
            e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity
            .status(e.getStatusCode())
            .body(errorResponse);
    }

    //잘못된 자료형으로 인한 에러
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> methodArgumentTypeMismatchExceptionError(
        MethodArgumentTypeMismatchException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.BAD_REQUEST, e);
        return ResponseEntity
            .status(ErrorCode.BAD_REQUEST.getStatus())
            .body(errorResponse);
    }

    //지원하지 않는 media type 에러
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> httpMediaTypeNotSupportedExceptionError(
        HttpMediaTypeNotSupportedException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.BAD_REQUEST, e);
        return ResponseEntity
            .status(e.getStatusCode())
            .body(errorResponse);
    }

    //외부 api client 에러
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> httpMediaTypeNotSupportedExceptionError(
        HttpClientErrorException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, e);
        return ResponseEntity
            .status(e.getStatusCode())
            .body(errorResponse);
    }

    //외부 api server 에러
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ErrorResponse> httpServerErrorExceptionError(HttpServerErrorException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, e);
        return ResponseEntity
            .status(e.getStatusCode())
            .body(errorResponse);
    }
}
