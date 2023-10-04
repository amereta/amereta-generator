package tech.amereta.generator.exception;

public class ModelDuplicateFieldNameException extends AbstractBadRequestException {

    public ModelDuplicateFieldNameException(String modelName, String fieldName) {
        super(String.format("Field %s is already exist in Model %s!", fieldName, modelName));
    }

    @Override
    public String getCode() {
        return "006";
    }
}
