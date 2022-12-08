package com.coderscampus.HotStonePOS.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.coderscampus.HotStonePOS.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query("select e from Employee e "
			+ " left join  fetch e.authorities "
			+ "where e.username = :username") 
	Employee findByUsername(@Param(value = "username") String username);
	
}
