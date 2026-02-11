package com.daniel.portscanner.postgres.repository;

import com.daniel.portscanner.postgres.entity.Host;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

//se crean los metodos: save, findById, findAll, deleteById

public interface HostRepository extends JpaRepository<Host, UUID> {
    Optional<Host> findByAddress(String address);
}
