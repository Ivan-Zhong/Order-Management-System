package com.zyf.oms.repositories;

import com.zyf.oms.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {

}
