package com.noa.pos.api.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityJsonCreated {

    @JsonCreator
    public SimpleGrantedAuthorityJsonCreated(@JsonProperty("authority") String role) {}
}
