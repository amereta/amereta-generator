package tech.amereta.generator.description.spring.model.type;

import lombok.Getter;

@Getter
public enum SpringRelation {

    ONE_TO_ONE("OneToOne"),
    ONE_TO_MANY("OneToMany"),
    MANY_TO_ONE("ManyToOne"),
    MANY_TO_MANY("ManyToMany");

    private final String name;

    SpringRelation(String name) {
        this.name = name;
    }
}
