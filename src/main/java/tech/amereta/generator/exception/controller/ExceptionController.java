package tech.amereta.generator.exception.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tech.amereta.generator.exception.AbstractBadRequestException;
import tech.amereta.generator.util.StringFormatter;

import java.util.Optional;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(AbstractBadRequestException.class)
    public ResponseEntity<ExceptionResponseModel> badRequest(AbstractBadRequestException e, HttpServletRequest request) {
        return new ResponseEntity<>(
                ExceptionResponseModel.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .code(e.getCode())
                        .reason(resolveMessage(e))
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    private static String resolveMessage(AbstractBadRequestException e) {
        return Optional.ofNullable(e.getMessage())
                .orElse(
                        StringFormatter.toHumaneCase(e.getClass().getSimpleName())
                );
    }
}
