package com.zyf.oms.repositories;

import com.zyf.oms.entities.ADOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ADOrderRepository extends JpaRepository<ADOrder, Integer> {
    @Query(value = "update adorder set clientname = ?1 where clientid = ?2", nativeQuery = true)
    @Modifying
    @Transactional
    public void updateClientname(String name, int id);

    @Query(value = "select * from adorder where status = ?1", nativeQuery = true)
    public List<ADOrder> findAllByStatus(String status);

    @Query(value = "select * from adorder where status = ?1 or status = ?2", nativeQuery = true)
    public List<ADOrder> findAllByStatus2(String status1, String status2);

    @Query(value = "select * from adorder where status = ?1 or status = ?2 or status = ?3", nativeQuery = true)
    public List<ADOrder> findAllByStatus3(String status1, String status2, String status3);
}
