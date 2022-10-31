package com.vladimir.java.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public record Item(int id, List<String> values) implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;
}
