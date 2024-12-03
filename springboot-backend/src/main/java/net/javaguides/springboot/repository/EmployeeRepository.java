package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.Employee;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    List<Employee> findByLastName(String lastname);

    List<Employee> findByEmailId(String email);
}
