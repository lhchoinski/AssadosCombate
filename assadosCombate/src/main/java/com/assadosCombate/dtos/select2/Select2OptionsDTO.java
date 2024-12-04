package com.assadosCombate.dtos.select2;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Select2OptionsDTO implements Serializable {

    private final Object value;
    private final Object label;

    public Select2OptionsDTO(Object idAndText) {
        this.value = idAndText;
        this.label = idAndText;
    }

    public Select2OptionsDTO(Object value, Object label) {
        this.value = value;
        this.label = label;
    }

}

