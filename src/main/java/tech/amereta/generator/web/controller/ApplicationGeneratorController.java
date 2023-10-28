package tech.amereta.generator.web.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import tech.amereta.generator.aspect.ApplicationDescriptionValidator;
import tech.amereta.generator.service.ApplicationGeneratorService;
import tech.amereta.lang.description.ApplicationDescriptionWrapper;

@RestController
@RequestMapping("/api")
public class ApplicationGeneratorController {

    @Autowired
    private ApplicationGeneratorService applicationGeneratorService;

    @PostMapping(
            value = "/generate",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    "application/yaml",
                    "text/yaml"
            },
            produces = "application/zip"
    )
    @ApplicationDescriptionValidator
    public ResponseEntity<StreamingResponseBody> generate(@Valid @RequestBody ApplicationDescriptionWrapper applicationDescriptionWrapper) {
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=" + applicationDescriptionWrapper.getApplication().getName() + ".zip")
                .body(out -> applicationGeneratorService.generate(applicationDescriptionWrapper, out));
    }
}
