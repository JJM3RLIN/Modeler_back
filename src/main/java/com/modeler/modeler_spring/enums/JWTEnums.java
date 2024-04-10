package com.modeler.modeler_spring.enums;

public enum JWTEnums {
    
    PREFIX_TOKEN("Bearer "),
    CREATE_AUTHORIZATION("Authorization"),
    CONTENT_TYPE("appliaction/json");
    private final String value;

    JWTEnums(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }
}
