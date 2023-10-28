package tech.amereta.generator.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.amereta.generator.service.ApplicationValidatorService;
import tech.amereta.lang.description.ApplicationDescriptionWrapper;

@Aspect
@Component
public class ApplicationDescriptionValidatorAspect {

    @Autowired
    private ApplicationValidatorService applicationValidatorService;

    @Pointcut("@annotation(tech.amereta.generator.aspect.ApplicationDescriptionValidator)")
    public void aroundApplicationDescriptionGenerator() {
    }

    @Around("aroundApplicationDescriptionGenerator()")
    public Object validateApplicationDescription(final ProceedingJoinPoint joinPoint) throws Throwable {
        final ApplicationDescriptionWrapper applicationDescriptionWrapper = (ApplicationDescriptionWrapper) joinPoint.getArgs()[0];
        applicationValidatorService.validate(applicationDescriptionWrapper);
        return joinPoint.proceed();
    }
}
