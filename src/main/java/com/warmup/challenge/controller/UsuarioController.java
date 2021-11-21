package com.warmup.challenge.controller;

import com.warmup.challenge.entity.Usuario;
import com.warmup.challenge.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario user){
        return new ResponseEntity<Usuario>(usuarioService.createUser(user), HttpStatus.CREATED);
    }

}
