package com.gabrielsmm.gestrec.adapter.persistence.jpa.repository;

import com.gabrielsmm.gestrec.adapter.persistence.jpa.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUsuarioRepo extends JpaRepository<UsuarioEntity, Long> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    Optional<UsuarioEntity> findByEmailIgnoreCase(String email);

}

