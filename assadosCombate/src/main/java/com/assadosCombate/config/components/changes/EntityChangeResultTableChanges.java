package com.assadosCombate.config.components.changes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntityChangeResultTableChanges {
    private String parameter;
    private String alias;
    private Object oldValue;
    private Object newValue;
}
