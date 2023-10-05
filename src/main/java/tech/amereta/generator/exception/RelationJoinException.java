package tech.amereta.generator.exception;

public class RelationJoinException extends AbstractBadRequestException {

    public RelationJoinException(String relation, String thisSide, String otherSide) {
        super(String.format("The %s relation between %s and %s has no or more than one join columns!", relation, thisSide, otherSide));
    }

    @Override
    public String getCode() {
        return "007";
    }
}
