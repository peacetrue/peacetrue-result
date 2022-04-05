package com.github.peacetrue.result.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author peace
 **/
@Slf4j
@Service
public class ExceptionConvertTestService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public void entityNotFound(Long id) {
        TestEntity entity = entityManager.getReference(TestEntity.class, id);
        //返回的是懒加载对象，必须访问一次，才能抛出异常
        log.trace("entityNotFound.name: {}", entity.getName());
    }

    @Transactional
    public void duplicate(TestEntity entity) {
        try {
            entityManager.persist(entity.clone());
            entityManager.persist(entity);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
