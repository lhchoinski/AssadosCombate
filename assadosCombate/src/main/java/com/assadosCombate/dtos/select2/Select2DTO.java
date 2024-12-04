package com.assadosCombate.dtos.select2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Select2DTO implements Serializable {

    private long total;
    private List<Select2OptionsDTO> results;

    public Select2DTO() {
        this.results = new ArrayList<>();
    }

    public void addResult(Select2OptionsDTO select2OptionsDTO){
        results.add(select2OptionsDTO);
        total++;
    }
}
