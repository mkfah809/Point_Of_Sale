package com.coderscampus.HotStonePOS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderscampus.HotStonePOS.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
