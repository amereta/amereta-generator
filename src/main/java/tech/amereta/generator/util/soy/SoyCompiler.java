package tech.amereta.generator.util.soy;

import com.google.template.soy.AbstractSoyCompiler;
import com.google.template.soy.SoyFileSet;
import com.google.template.soy.jbcsrc.api.SoySauce;

public class SoyCompiler extends AbstractSoyCompiler {

    private SoySauce soySauce;

    public SoyCompiler() {
    }

    @Override
    protected void compile(SoyFileSet.Builder soyFileSetBuilder) {
        soySauce = soyFileSetBuilder.build().compileTemplates();
    }

    public SoySauce getSoySauce() {
        return soySauce;
    }
}
