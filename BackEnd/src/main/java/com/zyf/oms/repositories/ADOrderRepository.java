package com.zyf.oms.repositories;

import com.zyf.oms.entities.ADOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ADOrderRepository extends JpaRepository<ADOrder, Integer> {
    @Query(value = "update adorder set clientname = ?1 where clientid = ?2", nativeQuery = true)
    @Modifying
    @Transactional
    public void updateClientname(String name, int id);
}
