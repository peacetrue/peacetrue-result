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

    @Transactional
    public void duplicateSQLException() {
        entityManager.persist(new TestEntity(null, "name"));
        entityManager.persist(new TestEntity(null, "name"));
    }

    @Transactional(readOnly = true)
    public void entityNotFoundException() {
        TestEntity entity = entityManager.getReference(TestEntity.class, -1L);
        //返回的是懒加载对象，必须访问一次，才能抛出异常
        log.trace("entityNotFoundException.name: {}", entity.getName());
    }
}
