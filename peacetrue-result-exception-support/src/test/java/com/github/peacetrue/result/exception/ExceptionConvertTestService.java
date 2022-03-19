package com.github.peacetrue.result.exception;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author peace
 **/
@Service
public class ExceptionConvertTestService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void duplicateSQLException() {
        entityManager.persist(new TestEntity(null, "name"));
        entityManager.persist(new TestEntity(null, "name"));
    }
}
