package org.example.dienluc.repository;

import org.example.dienluc.entity.Account;
import org.example.dienluc.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    public Optional<Account> findByUsername(String username);
    public Optional<Account> findByUsernameAndPasswordAndRole(String username, String password, Role role);
}
