package com.warmup.challenge.repository;

import com.warmup.challenge.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Usuario findUsuarioByEmail(String email);
}
