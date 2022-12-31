package com.coderscampus.HotStonePOS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.coderscampus.HotStonePOS.domain.Topping;

@Repository
public interface ToppingRepo extends JpaRepository<Topping, Long> {

	Topping findByName(String name);

	@Query("select t from Topping t left join fetch t.pizzas p where p.pizzaId=:id")
	List<Topping> findAllByPizza(Long id);
}
