package com.banking.thejavabanking.exceptions;

import com.banking.thejavabanking.dto.respones.BaseResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final String MIN_KEY = "min";
    private static final String LENGTH_KEY = "length";

    @ExceptionHandler({Exception.class})
    ResponseEntity<BaseResponse> handleRuntimeException(RuntimeException e) {
        BaseResponse response = new BaseResponse(
                ErrorResponse.UNCATEGORIZED.getCode(),
//                ErrorResponse.UNCATEGORIZED.getMessage()
                e.getMessage()
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<BaseResponse> handleAppException(AppException e) {
        ErrorResponse errorResponse = e.getErrorResponse();

        BaseResponse response = new BaseResponse(
                errorResponse.getCode(),
                errorResponse.getMessage()
        );

        return ResponseEntity.status(errorResponse.getHttpStatusCode()).body(response);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<BaseResponse> handleAccessDeniedException(AccessDeniedException e) {
        BaseResponse response = new BaseResponse(
                ErrorResponse.UNAUTHORIZED.getCode(),
                ErrorResponse.UNAUTHORIZED.getMessage()
        );

        return ResponseEntity.status(ErrorResponse.UNAUTHORIZED.getHttpStatusCode()).body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        String enumsKey = e.getFieldError().getDefaultMessage();

        ErrorResponse errorCode = ErrorResponse.valueOf(enumsKey);
        Map<String, Object> attributes = null;

        try {
            errorCode = ErrorResponse.valueOf(enumsKey);
            if (errorCode == ErrorResponse.INVALID_DOB
                    || errorCode == ErrorResponse.PASSWORD_INVALID
                    || errorCode == ErrorResponse.INVALID_PHONE_NUMBER
                    || errorCode == ErrorResponse.INVALID_ACCOUNT_NUMBER
            ) {
                var constraint = e.getBindingResult().getAllErrors().get(0).unwrap(
                        ConstraintViolation.class);
                attributes = constraint.getConstraintDescriptor().getAttributes();
                log.info("Attributes : {}", attributes);
            }
        } catch (Exception ex) {
            log.error("Error while parsing error code", ex);
        }

        BaseResponse apiResponse = new BaseResponse();

        apiResponse.setStatus(errorCode.getCode());
        apiResponse.setMessage(Objects.nonNull(attributes)
                                       ? mapAttribute(errorCode.getMessage(), attributes)
                                       : errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    // CHUA BAT EXCEPTION ENUMS PROVINCE
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;
            if (ife.getTargetType().isEnum()) {
                return "Invalid value for enum " + ife.getTargetType()
                                                      .getSimpleName() + ": " + ife.getValue();
            }
        }
        return ex.getMessage();
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        return message.replace("{" + MIN_KEY + "}", attributes.get(MIN_KEY).toString());
    }
}
