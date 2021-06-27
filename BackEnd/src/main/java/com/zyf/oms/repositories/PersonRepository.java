package com.zyf.oms.repositories;

import com.zyf.oms.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    @Query(value = "select * from person where name = ?1", nativeQuery = true)
    public List<Person> findAllByName(String name);
}
