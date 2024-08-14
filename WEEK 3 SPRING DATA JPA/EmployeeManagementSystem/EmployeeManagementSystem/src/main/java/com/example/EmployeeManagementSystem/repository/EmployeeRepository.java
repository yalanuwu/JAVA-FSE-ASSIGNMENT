// package com.example.EmployeeManagementSystem.repository;

import com.example.EmployeeManagementSystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Find employees by their name
    List<Employee> findByName(String name);

    // Find employees by their email
    Optional<Employee> findByEmail(String email);

    // Find all employees in a specific department
    List<Employee> findByDepartmentId(Long departmentId);

    @Query("SELECT e FROM Employee e WHERE e.name LIKE %:name%")
    List<Employee> findByNameContaining(@Param("name") String name);

    @Query("SELECT e FROM Employee e WHERE e.department.name = :departmentName")
    List<Employee> findByDepartmentName(@Param("departmentName") String departmentName);
}
