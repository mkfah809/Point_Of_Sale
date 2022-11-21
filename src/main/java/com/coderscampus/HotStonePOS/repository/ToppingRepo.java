package com.coderscampus.HotStonePOS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderscampus.HotStonePOS.domain.Topping;

@Repository
public interface ToppingRepo extends JpaRepository<Topping, Long> {

}
