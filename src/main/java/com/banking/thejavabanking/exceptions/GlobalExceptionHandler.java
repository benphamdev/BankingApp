package com.banking.thejavabanking.exceptions;

import com.banking.thejavabanking.dto.respones.shared.BaseResponse;
import com.banking.thejavabanking.dto.respones.shared.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Objects;

import static com.banking.thejavabanking.dto.respones.shared.ApiError.createFieldApiError;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String MIN_KEY = "min";
    private static final String LENGTH_KEY = "length";
    ErrorResponse errorResponse = new ErrorResponse();
    String message;

    /**
     * Handle exception when internal server error
     *
     * @param e
     * @param request
     * @return ErrorResponse
     */
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "500", description = "Internal Server Error",
                            content = {@Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(
                                            name = "500 Response",
                                            summary = "Handle exception when internal server error",
                                            value = """
                                                    {
                                                      "timestamp": "2023-10-19T06:35:52.333+00:00",
                                                      "status": 500,
                                                      "path": "/api/v1/...",
                                                      "error": "Internal Server Error",
                                                      "message": "Connection timeout, please try again"
                                                    }
                                                    """
                                    )
                            )}
                    )
            }
    )
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e, WebRequest request) {
        initErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                request
        );
        message = e.getMessage();
        errorResponse.setMessage(message);
        return errorResponse;
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<BaseResponse<?>> handleAppException(AppException e) {
        EnumsErrorResponse errorResponse = e.getErrorResponse();

        BaseResponse<?> response = new BaseResponse<>(
                errorResponse.getCode(),
                errorResponse.getMessage()
        );

        return ResponseEntity.status(errorResponse.getHttpStatusCode())
                             .body(response);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<BaseResponse<?>> handleAccessDeniedException(AccessDeniedException e) {
        BaseResponse<?> response = new BaseResponse<>(
                EnumsErrorResponse.UNAUTHORIZED.getCode(),
                EnumsErrorResponse.UNAUTHORIZED.getMessage()
        );

        return ResponseEntity.status(EnumsErrorResponse.UNAUTHORIZED.getHttpStatusCode())
                             .body(response);
    }

    //  BAT EXCEPTION ENUMS PROVINCE
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, WebRequest request
    ) {
        initErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                request
        );
        message = ex.getMessage();

        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException ife) {
            if (ife.getTargetType()
                   .isEnum()) {
//                message = "Invalid value for enum " + ife.getTargetType()
//                                                         .getSimpleName() + ": " + ife.getValue();
                int start = message.lastIndexOf('"');
                message = message.substring(start + 3);
            }
        }

        errorResponse.setMessage(message);
        return errorResponse;
    }

    /**
     * Handle exception when the data is conflicted
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(CONFLICT)
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "409", description = "Conflict",
                            content = {@Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(
                                            name = "409 Response",
                                            summary = "Handle exception when input data is conflicted",
                                            value = """
                                                    {
                                                      "timestamp": "2023-10-19T06:07:35.321+00:00",
                                                      "status": 409,
                                                      "path": "/api/v1/...",
                                                      "error": "Conflict",
                                                      "message": "{data} exists, Please try again!"
                                                    }
                                                    """
                                    )
                            )}
                    )
            }
    )
    public ErrorResponse handleDuplicateKeyException(InvalidDataException e, WebRequest request) {
        // way throw exception in service layer and try catch in controller layer
        initErrorResponse(CONFLICT.value(), CONFLICT.getReasonPhrase(), request);
        message = e.getMessage();
        errorResponse.setMessage(message);
        return errorResponse;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolationException(
            DataIntegrityViolationException e, WebRequest request
    ) {
        initErrorResponse(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                request
        );
        String errorMessage = Objects.requireNonNull(e.getRootCause())
                                     .getMessage();

//        if (errorMessage.contains("Duplicate entry")) {
        int startIndex = errorMessage.indexOf("'") + 1;
        int endIndex = errorMessage.indexOf("'", startIndex);
        String duplicateValue = errorMessage.substring(startIndex, endIndex);
        duplicateValue = duplicateValue.contains("@")
                ? String.format("Email : %s %s", duplicateValue, EnumsErrorResponse.EMAIL_EXISTS)
                : String.format(
                "Phone number : %s %s",
                duplicateValue,
                EnumsErrorResponse.PHONE_NUMBER_EXISTS
        );
        errorResponse.setMessage(duplicateValue);
//        }
        return errorResponse;
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        return message.replace(
                "{" + MIN_KEY + "}",
                attributes.get(MIN_KEY)
                          .toString()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<BaseResponse<?>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e,
            WebRequest request
    ) {
        initErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                request
        );
        message = e.getMessage();

        errorResponse.setError("Payload validation failed");

        String enumsKey = Objects.requireNonNull(e.getFieldError())
                                 .getDefaultMessage();

        assert enumsKey != null;
        if (enumsKey.contains("enum")) {
            return ResponseEntity.badRequest()
                                 .body(new BaseResponse<>(BAD_REQUEST.value(), enumsKey));
        }

        var apiErrors = e.getFieldErrors()
                         .stream()
                         .map(v -> createFieldApiError(
                                 v.getField(),
                                 getMessageInvalidValue(e, v.getDefaultMessage()),
                                 request.getDescription(false)
                                        .replace("uri=", ""),
                                 v.getRejectedValue()
                         ))
                         .toList();

        return ResponseEntity.badRequest()
                             .body(new BaseResponse<>(
                                     errorResponse.getStatus(),
                                     errorResponse.getError(),
                                     apiErrors
                             ));
    }

    private String getMessageInvalidValue(
            MethodArgumentNotValidException e, String message
    ) {
//        log.info("Message : {}", message);
//        String enumsKey = Objects.requireNonNull(e.getFieldError())
//                                 .getDefaultMessage();
        EnumsErrorResponse errorCode = EnumsErrorResponse.valueOf(message);
        Map<String, Object> attributes = null;
        try {
            errorCode = EnumsErrorResponse.valueOf(message);
            if (errorCode == EnumsErrorResponse.INVALID_DOB
                    || errorCode == EnumsErrorResponse.PASSWORD_INVALID
                    || errorCode == EnumsErrorResponse.INVALID_PHONE_NUMBER
                    || errorCode == EnumsErrorResponse.INVALID_ACCOUNT_NUMBER
            ) {
                var constraint = Objects.requireNonNull(e.getBindingResult()
                                                         .getFieldError())
                                        .unwrap(ConstraintViolation.class);

                attributes = constraint.getConstraintDescriptor()
                                       .getAttributes();

                log.info("Attributes : {}", attributes);
            }
        } catch (Exception ex) {
            log.error("Error while parsing error code", ex);
        }

        return Objects.nonNull(attributes)
                ? mapAttribute(errorCode.getMessage(), attributes)
                : errorCode.getMessage();
    }

    /**
     * Handle exception when the data invalid. (@RequestBody, @RequestParam, @PathVariable)
     *
     * @param e
     * @param request
     * @return ErrorResponse
     */
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "400", description = "Bad Request",
                            content = {@Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(
                                            name = "Handle exception when the data invalid. (@RequestBody, @RequestParam, @PathVariable)",
                                            summary = "Handle Bad Request",
                                            value = """
                                                    {
                                                         "timestamp": "2024-04-07T11:38:56.368+00:00",
                                                         "status": 400,
                                                         "path": "/api/v1/...",
                                                         "error": "Invalid Payload",
                                                         "message": "{data} must be not blank"
                                                     }
                                                    """
                                    )
                            )}
                    )
            }
    )
    @ExceptionHandler(
            {
//                    MethodArgumentNotValidException.class,
                    ConstraintViolationException.class,
                    MissingServletRequestParameterException.class
            }
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<?> handleValidationException(Exception e, WebRequest request) {
        initErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                request
        );
        message = e.getMessage();
        if (e instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
//            int start = message.lastIndexOf("[");
//            int end = message.lastIndexOf("]");
//            message = message.substring(start + 1, end - 1);
            errorResponse.setError("Payload validation failed");

            var apiErrors = methodArgumentNotValidException.getFieldErrors()
                                                           .stream()
                                                           .map(v -> createFieldApiError(
                                                                   v.getField(),
                                                                   v.getDefaultMessage(),
                                                                   request.getDescription(false)
                                                                          .replace("uri=", ""),
                                                                   v.getRejectedValue()
                                                           ))
                                                           .toList();

            return new BaseResponse<>(
                    errorResponse.getStatus(),
                    errorResponse.getError(),
                    apiErrors
            );
        } else if (e instanceof ConstraintViolationException) {
            message = message.substring(message.indexOf(" ") + 1);
            errorResponse.setError("PathVariable validation failed");
        } else if (e instanceof MissingServletRequestParameterException) {
            errorResponse.setError("RequestParam validation failed");
        }
        errorResponse.setMessage(message);
        return new BaseResponse<>(
                errorResponse.getStatus(),
                errorResponse.getError(),
                errorResponse
        );
    }

    /**
     * Handle exception when the request not found data
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "404", description = "Bad Request",
                            content = {@Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(
                                            name = "404 Response",
                                            summary = "Handle exception when resource not found",
                                            value = """
                                                    {
                                                      "timestamp": "2023-10-19T06:07:35.321+00:00",
                                                      "status": 404,
                                                      "path": "/api/v1/...",
                                                      "error": "Not Found",
                                                      "message": "{data} not found"
                                                    }
                                                    """
                                    )
                            )}
                    )
            }
    )
    public ErrorResponse handleResourceNotFoundException(
            ResourceNotFoundException e, WebRequest request
    ) {
        initErrorResponse(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase(), request);
        errorResponse.setMessage(e.getMessage());
        return errorResponse;
    }

    private void initErrorResponse(int status, String error, WebRequest request) {
        errorResponse.setStatus(status);
        errorResponse.setPath(request.getDescription(false)
                                     .replace("uri=", ""));//request.getContextPath());
        errorResponse.setError(error);
    }
}
