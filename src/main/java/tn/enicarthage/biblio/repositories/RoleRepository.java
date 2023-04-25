package tn.enicarthage.biblio.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import tn.enicarthage.biblio.Entities.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String nom);
}