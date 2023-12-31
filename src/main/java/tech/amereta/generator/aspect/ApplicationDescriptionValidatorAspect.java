package tech.amereta.generator.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;
import tech.amereta.generator.description.AbstractApplication;
import tech.amereta.generator.description.ApplicationDescription;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.service.ApplicationGeneratorService;
import tech.amereta.generator.service.ApplicationValidatorService;

import java.util.Objects;

@Aspect
@Component
public class ApplicationDescriptionValidatorAspect {

    @Autowired
    private ApplicationValidatorService applicationValidatorService;

    @Pointcut("@annotation(tech.amereta.generator.aspect.ApplicationDescriptionValidator)")
    public void aroundApplicationDescriptionGenerator() {}

    @Around("aroundApplicationDescriptionGenerator()")
    public Object validateApplicationDescription(final ProceedingJoinPoint joinPoint) throws Throwable {
        final ApplicationDescription applicationDescription = (ApplicationDescription) joinPoint.getArgs()[0];
        applicationValidatorService.validate(applicationDescription);
        return joinPoint.proceed();
    }
}
