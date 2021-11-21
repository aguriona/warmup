package com.warmup.challenge.service;

import com.warmup.challenge.entity.Usuario;
import com.warmup.challenge.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario createUser(Usuario user) {
    return usuarioRepository.save(user);
    }
}
