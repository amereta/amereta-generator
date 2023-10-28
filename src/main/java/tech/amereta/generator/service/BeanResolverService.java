package tech.amereta.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

@Service
public class BeanResolverService {

    @Autowired
    private ApplicationContext context;

    public <T> Collection<T> findGeneratorByAnnotation(Class<T> clazz, Class<? extends Annotation> annotationType) {
        Map<String, T> typedBeans = this.context.getBeansOfType(clazz);
        Map<String, Object> annotatedBeans = this.context.getBeansWithAnnotation(annotationType);
        typedBeans.keySet().retainAll(annotatedBeans.keySet());
        return typedBeans.values();
    }
}
