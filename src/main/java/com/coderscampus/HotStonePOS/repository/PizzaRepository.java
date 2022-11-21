package com.coderscampus.HotStonePOS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.coderscampus.HotStonePOS.domain.Pizza;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {

	@Query("select p from Pizza p left join fetch p.orders o where o.id=:id")
	List<Pizza> findAllByOrder(Long id);

}
