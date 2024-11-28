package com.assadosCombate.config.components.pageable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CFilter {
    private String column;
    private Object value;
}
