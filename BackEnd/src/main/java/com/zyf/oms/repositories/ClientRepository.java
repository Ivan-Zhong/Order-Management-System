package com.zyf.oms.repositories;

import com.zyf.oms.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    @Query(value = "select * from client where name = ?1", nativeQuery = true)
    public List<Client> getClientByName(String name);
    @Transactional
    @Query(value = "update client set name = ?1, contactname = ?2, tel = ?3, address = ?4 where id = ?5", nativeQuery = true)
    @Modifying
    public void updateClient(String name, String contactname, String tel, String address, int id);
}
