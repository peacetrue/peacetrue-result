package com.github.peacetrue.result.exception.quick_start;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author peace
 **/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
