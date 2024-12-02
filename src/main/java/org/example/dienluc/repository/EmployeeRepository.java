package org.example.dienluc.repository;

import org.example.dienluc.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("select e from Employee e where e.account.role.id = ?1 and e.resignation = false")
    List<Employee> findByRoleId(Integer roleId);

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByPhone(String phone);

    Optional<Employee> findByIdentityCard(String identityCard);
}
