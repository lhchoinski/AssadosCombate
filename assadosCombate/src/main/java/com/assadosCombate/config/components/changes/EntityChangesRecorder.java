package com.assadosCombate.config.components.changes;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityChangesRecorder extends EntityChanges {

    private static final ThreadLocal<Map<Object, Object[]>> changes = ThreadLocal.withInitial(HashMap::new);

    public static void recordChange(Object oldEntity, Object newEntity) {
        changes.get().put(newEntity, new Object[]{oldEntity, newEntity});
    }

    public static void registerSynchronization(EntityChangeCallback callback) {
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            return;
        }

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if (status == TransactionSynchronization.STATUS_COMMITTED && !changes.get().isEmpty()) {
                    Map<Object, Object[]> entityChangesMap = changes.get();
                    List<EntityChangeResultTable> entityChangeResultTableList = new ArrayList<>();

                    for (Map.Entry<Object, Object[]> entry : entityChangesMap.entrySet()) {
                        Object[] oldAndNew = entry.getValue();

                        try {
                            EntityChangeResultTable entityChangeResultTable = compareTables(oldAndNew[0], oldAndNew[1]);

                            if (!entityChangeResultTable.getChanges().isEmpty()) {
                                entityChangeResultTableList.add(entityChangeResultTable);
                            }
                        } catch (
                                IllegalAccessException |
                                IntrospectionException |
                                InvocationTargetException e
                        ) {
                            System.out.println("::::::::::::::::::>>> ERROR " + e.getMessage());
                        }
                    }

                    callback.onEntityChange(entityChangeResultTableList);
                }

                changes.remove();
            }
        });
    }

}
