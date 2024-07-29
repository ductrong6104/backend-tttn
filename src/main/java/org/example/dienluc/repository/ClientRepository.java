package org.example.dienluc.repository;

import org.example.dienluc.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    public Optional<Client> findByEmail(String email);
    public Optional<Client> findByPhone(String phone);
    public Optional<Client> findByIdentityCard(String identityCard);
}
