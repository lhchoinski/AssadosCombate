package com.assadosCombate.config.components.changes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntityChangeResultTableRefChanges {
    private Map<String, List<EntityChangeResultTableChanges>> changes;
}
