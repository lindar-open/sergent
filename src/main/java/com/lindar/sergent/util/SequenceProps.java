package com.lindar.sergent.util;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(fluent = true)
public class SequenceProps {
    private int size;
    private int min;
    private int max;
    private boolean unique;
}
