package tech.amereta.generator.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Builder
@Data
public class ExceptionResponseModel {

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    private ZonedDateTime timestamp = ZonedDateTime.now();
    private int status;
    private String error;
    private String code;
    private String reason;
    private String path;
}
