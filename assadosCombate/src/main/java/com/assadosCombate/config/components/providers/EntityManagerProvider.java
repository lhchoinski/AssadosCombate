package com.assadosCombate.config.components.providers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityManagerProvider implements ApplicationListener<ContextRefreshedEvent> {

    @Getter
    private static EntityManager entityManager;

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        entityManager = entityManagerFactory.createEntityManager();
    }

}
