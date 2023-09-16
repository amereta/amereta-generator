package tech.amereta.generator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import tech.amereta.generator.domain.description.ApplicationDescription;
import tech.amereta.generator.service.ApplicationGeneratorService;

@RestController
@RequestMapping("/api")
public class ApplicationGeneratorController {

    @Autowired
    private ApplicationGeneratorService applicationGeneratorService;

    @PostMapping(value = "/generate", produces = "application/zip")
    public ResponseEntity<StreamingResponseBody> generate(@RequestBody ApplicationDescription applicationDescription) {
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=" + applicationDescription.getApplication().getName() + ".zip")
                .body(out -> applicationGeneratorService.generate(applicationDescription, out));
    }
}
