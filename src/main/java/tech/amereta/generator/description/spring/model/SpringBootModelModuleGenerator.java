package tech.amereta.generator.description.spring.model;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface SpringBootModelModuleGenerator {
}
