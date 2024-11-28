package com.assadosCombate.config.components.changes;

import java.util.List;

public interface EntityChangeCallback {
    void onEntityChange(List<EntityChangeResultTable> entityChangesList);
}
