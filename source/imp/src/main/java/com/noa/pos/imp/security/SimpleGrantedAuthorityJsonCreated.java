package com.noa.pos.imp.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityJsonCreated {

    @JsonCreator
    public SimpleGrantedAuthorityJsonCreated(@JsonProperty("authority") String role) {}
}
