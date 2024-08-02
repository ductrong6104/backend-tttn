package org.example.dienluc.repository;

import org.example.dienluc.entity.Account;
import org.example.dienluc.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    public Optional<Account> findByUsername(String username);
    public Optional<Account> findByUsernameAndPasswordAndRole(String username, String password, Role role);
    @Query("SELECT a from Account a where a.role.id = ?1")
    public List<Account> findByRoleId(Integer id);
    public Boolean existsByUsername(String username);
    public Integer deleteByUsername(String username);
}
