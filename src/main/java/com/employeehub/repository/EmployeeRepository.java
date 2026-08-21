package com.employeehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeehub.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}