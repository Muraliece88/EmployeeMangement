package com.abnamro.nl.repo;

import com.abnamro.nl.entities.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    @Procedure(procedureName = "DELETE_ROLE_SP")
    void deleteRoleSp(@Param("role_id") Long roleId, @Param("id") Long defaultEmpId);

}
