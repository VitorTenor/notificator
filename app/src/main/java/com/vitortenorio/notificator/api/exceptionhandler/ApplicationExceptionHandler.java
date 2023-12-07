package com.vitortenorio.notificator.api.exceptionhandler;

import com.vitortenorio.notificator.core.util.MessageHelper;
import com.vitortenorio.notificator.enums.ProblemType;
import com.vitortenorio.notificator.exception.EmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageHelper messageHelper;

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                            HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                    HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers, HttpStatus status,
                                                                WebRequest request, BindingResult bindingResult) {
        var problemType = ProblemType.INVALID_FIELD;
        final var ONE_OR_MORE_FIELDS_ARE_INVALID = messageHelper.getMessage("invalid_one_or_more_field");

        List<Problem.Object> list = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String message = objectError.getDefaultMessage();
                    String name = objectError.getObjectName();
                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }
                    return Problem.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .collect(java.util.stream.Collectors.toList());

        var problem = createProblem(status, problemType, ONE_OR_MORE_FIELDS_ARE_INVALID);
        problem.setObjects(list);

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<Object> handleEmailException(EmailException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var problemType = ProblemType.SEND_EMAIL_ERROR;
        final var ERROR_SENDING_EMAIL = messageHelper.getMessage("error_sending_email");

        Problem problem = createProblem(status, problemType, ERROR_SENDING_EMAIL);

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private Problem createProblem(HttpStatus status, ProblemType problemType, String detail) {
        return Problem.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .title(problemType.getTitle())
                .detail(detail)
                .userMessage(detail)
                .uri(problemType.getUri())
                .build();
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage(), ex);

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
        }

        return new ResponseEntity<>(body, headers, status);
    }
}
