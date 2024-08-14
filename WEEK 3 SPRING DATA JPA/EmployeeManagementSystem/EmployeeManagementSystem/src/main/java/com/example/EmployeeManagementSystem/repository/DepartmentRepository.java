// package com.example.EmployeeManagementSystem.repository;

import com.example.EmployeeManagementSystem.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    
    Optional<Department> findByName(String name);

    @Query("SELECT d FROM Department d WHERE d.name LIKE %:substring%")
    List<Department> findByNameContaining(@Param("substring") String substring);
}

