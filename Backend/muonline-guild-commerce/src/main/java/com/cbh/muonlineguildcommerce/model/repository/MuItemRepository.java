package com.cbh.muonlineguildcommerce.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbh.muonlineguildcommerce.model.entity.MuItem;

@Repository
public interface MuItemRepository extends JpaRepository<MuItem, Long> {

}
