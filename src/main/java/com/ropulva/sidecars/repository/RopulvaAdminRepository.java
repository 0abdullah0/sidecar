package com.ropulva.sidecars.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ropulva.sidecars.model.RopulvaAdmin;

@Repository
public interface RopulvaAdminRepository extends JpaRepository<RopulvaAdmin, String> {

	Optional<RopulvaAdmin> findByUsername(String username);
}
