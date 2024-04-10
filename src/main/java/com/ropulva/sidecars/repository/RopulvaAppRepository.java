package com.ropulva.sidecars.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ropulva.sidecars.model.RopulvaApp;

@Repository
public interface RopulvaAppRepository extends JpaRepository<RopulvaApp, String> {

	Optional<RopulvaApp> findById(String appId);

}
