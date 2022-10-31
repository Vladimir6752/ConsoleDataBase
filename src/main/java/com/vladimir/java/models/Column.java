package com.vladimir.java.models;

import java.io.Serial;
import java.io.Serializable;

public record Column(int id, String name) implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;
}
