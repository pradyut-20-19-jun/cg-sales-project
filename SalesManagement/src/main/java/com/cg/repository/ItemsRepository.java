package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.Items;

public interface ItemsRepository extends JpaRepository<Items, Integer> {

}
