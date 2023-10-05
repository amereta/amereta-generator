package tech.amereta.generator.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@NoArgsConstructor
public abstract class AbstractBadRequestException extends RuntimeException {

    public AbstractBadRequestException(String message) {
        super(message);
    }

    public abstract String getCode();
}
