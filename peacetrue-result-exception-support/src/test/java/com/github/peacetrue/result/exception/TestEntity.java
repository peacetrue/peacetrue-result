package com.github.peacetrue.result.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author peace
 **/
@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uk_name", columnNames = "name"))
@NoArgsConstructor
@AllArgsConstructor
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, unique = true)
    private String name;
}
