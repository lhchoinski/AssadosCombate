package com.assadosCombate.config.components.changes;

import com.assadosCombate.config.components.providers.EntityManagerProvider;
import com.assadosCombate.helpers.JsonHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;


@Component
public class EntityChangeListener {

    @PreUpdate
    public void preUpdate(Object entity) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        Object entityId = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
        Object oldEntity = entityManager.find(entity.getClass(), entityId);
        EntityChangesRecorder.recordChange(oldEntity, entity);
    }

    @PostUpdate
    public void postUpdate(Object entity) {
        EntityChangeCallback callback = entityChangesList -> {
            String jsonChanges = JsonHelper.convertToJson(entityChangesList);
            System.out.println("JSON Changes: " + jsonChanges);
        };

        EntityChangesRecorder.registerSynchronization(callback);
    }
}
