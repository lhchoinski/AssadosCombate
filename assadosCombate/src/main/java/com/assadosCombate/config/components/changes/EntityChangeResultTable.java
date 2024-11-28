package com.assadosCombate.config.components.changes;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EntityChangeResultTable {
    private String table;
    private Object id;
    private List<EntityChangeResultTableChanges> changes;
}
