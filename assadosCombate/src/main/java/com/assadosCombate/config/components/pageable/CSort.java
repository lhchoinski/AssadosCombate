package com.assadosCombate.config.components.pageable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CSort {
    private String column;
    private String direction = "asc";

    public CSort(String column){
        this.column = column;
    }
}
