package tech.amereta.generator.util;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

public class YamlConverter extends AbstractJackson2HttpMessageConverter {

    public YamlConverter() {
        super(new YAMLMapper(), MediaType.parseMediaType("application/yaml"), MediaType.parseMediaType("text/yaml"));
    }
}
