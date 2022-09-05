package com.aniflix.api.domain.model.type;

public enum EventType {
    
    IN("I"),
    OUT("O");

    private final String type;

    EventType(final String type) {

        this.type = type;
    }

    public String value() {

        return type;
    }

    public static EventType of(final String type) {

        return type.equalsIgnoreCase("I") ? IN
            : OUT;
    }
}
