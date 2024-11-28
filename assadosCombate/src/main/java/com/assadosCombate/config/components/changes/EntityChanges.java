package com.assadosCombate.config.components.changes;

import jakarta.persistence.Entity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class EntityChanges {

    public static Object unproxy(Object entity) {
        if (entity instanceof HibernateProxy) {
            return Hibernate.unproxy(entity);
        }

        return entity;
    }

    public static Object getId(Object entity) throws IllegalAccessException, InvocationTargetException {
        if (entity == null) {
            return null;
        }

        PropertyDescriptor idPropertyDescriptor = getIdPropertyDescriptor(entity.getClass());
        if (idPropertyDescriptor != null) {
            return idPropertyDescriptor.getReadMethod().invoke(entity);
        }

        return null;
    }

    public static PropertyDescriptor getIdPropertyDescriptor(Class<?> entityClass) {
        try {
            PropertyDescriptor[] propertyDescriptors = java.beans.Introspector.getBeanInfo(entityClass, Object.class).getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if ("id".equals(propertyDescriptor.getName())) {
                    return propertyDescriptor;
                }
            }
        } catch (Exception ignored) {
        }

        return null;
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    public static EntityChangeResultTable compareTables(Object oldEntity, Object newEntity) throws InvocationTargetException, IllegalAccessException, IntrospectionException {
        EntityChangeResultTable entityChangeResultTable = new EntityChangeResultTable();
        entityChangeResultTable.setTable(oldEntity.getClass().getSimpleName());

        // Obtendo o ID da entidade
        Object id = getId(newEntity);
        entityChangeResultTable.setId(id != null ? id.toString() : null);
        entityChangeResultTable.setChanges(compareEntities(oldEntity, newEntity));

        return entityChangeResultTable;
    }

    public static List<EntityChangeResultTableChanges> compareEntities(Object oldEntity, Object newEntity) throws IllegalAccessException, InvocationTargetException, IntrospectionException {
        oldEntity = unproxy(oldEntity);
        newEntity = unproxy(newEntity);

        if (!Objects.equals(oldEntity.getClass(), newEntity.getClass())) {
            throw new IllegalArgumentException("Entities must be of the same type " + oldEntity.getClass().getName() + " and " + newEntity.getClass().getName());
        }

        List<EntityChangeResultTableChanges> changes = new ArrayList<>();

        PropertyDescriptor[] propertyDescriptors = java.beans.Introspector.getBeanInfo(oldEntity.getClass(), Object.class).getPropertyDescriptors();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            log.info("EntityChanges | propertyName {}", propertyName);

            Object oldValue = propertyDescriptor.getReadMethod().invoke(oldEntity);
            Object newValue = propertyDescriptor.getReadMethod().invoke(newEntity);

            Field field = getField(oldEntity.getClass(), propertyName);

            if (field == null) {
                continue;
            }

            field.setAccessible(true);

            // Check if the property has @EntityChangeIgnoreField
            if (field.isAnnotationPresent(EntityChangeIgnoreField.class)) {
                log.info("EntityChanges | propertyName {} IGNORE FIELD", propertyName);
                continue;
            }

            String fieldAlias = null;

            if (field.isAnnotationPresent(EntityChangeFieldAlias.class)) {
                fieldAlias = field.getAnnotation(EntityChangeFieldAlias.class).value();
                log.info("EntityChanges | propertyName {} FIELD ALIAS {}", propertyName, fieldAlias);
            }

            if (field.getType().isAnnotationPresent(Entity.class)) {
                Object oldEntityId = getId(oldValue);
                Object newEntityId = getId(newValue);

                log.info("EntityChanges | propertyName {} COMPARE ENTITY {} {}", propertyName, oldEntityId, newEntityId);

                if (!Objects.equals(oldEntityId, newEntityId)) {
                    log.info("EntityChanges | propertyName {} SAVE DATA", propertyName);

                    EntityChangeResultTableChanges change = new EntityChangeResultTableChanges();
                    change.setParameter(propertyName + ".id");
                    change.setAlias(fieldAlias);
                    change.setOldValue(oldEntityId != null ? oldEntityId.toString() : null);
                    change.setNewValue(newEntityId != null ? newEntityId.toString() : null);
                    changes.add(change);
                }

                continue;
            }

            log.info("EntityChanges | propertyName {} COMPARE DATA {} {}", propertyName, oldValue, newValue);

            if (!Objects.equals(oldValue, newValue)) {
                log.info("EntityChanges | propertyName {} SAVE DATA", propertyName);

                EntityChangeResultTableChanges change = new EntityChangeResultTableChanges();
                change.setParameter(propertyName);
                change.setAlias(fieldAlias);
                change.setOldValue(oldValue != null ? oldValue.toString() : null);
                change.setNewValue(newValue != null ? newValue.toString() : null);
                changes.add(change);
            }
        }

        return changes;
    }
}
