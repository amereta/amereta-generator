package tech.amereta.generator.description.spring.db.type;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface SpringBootPostgreSQLDBModuleGenerator {
}
