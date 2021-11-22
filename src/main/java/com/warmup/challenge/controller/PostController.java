package com.warmup.challenge.controller;

import com.warmup.challenge.entity.Post;
import com.warmup.challenge.entity.PostDto;
import com.warmup.challenge.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam(value = "titulo", required = false) String titulo,
                                      @RequestParam(value = "categoria", required = false) String categoria) {


        if (titulo != null && titulo.length() > 0) {
            List<PostDto> postList = postService.filtrarTitulo(titulo);
            if (postList.isEmpty()) {
                return new ResponseEntity<>("No existe ese titulo", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<List<PostDto>>(postService.filtrarTitulo(titulo), HttpStatus.OK);
            }
        }
        if (categoria != null && categoria.length() > 0) {
            List<PostDto> postList = postService.filtrarCategoria(categoria);
            if (postList.isEmpty()) {
                return new ResponseEntity<>("No existe esa categoria", HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<List<PostDto>>(postService.filtrarCategoria(categoria), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<List<PostDto>>(postService.getPostsByFechaCreacion(), HttpStatus.OK);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById (@PathVariable Long id){
        return new ResponseEntity<Post>(postService.getPostById(id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        postService.deltePost(id);
        return new ResponseEntity<>("Eliminado",HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<?> savePost(@Valid @RequestBody Post post, BindingResult result){
        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> {
                        return "Error en el campo: " + err.getField() + ": " + err.getDefaultMessage();
                    })
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        } else{
            return new ResponseEntity<Post>(postService.createPost(post),HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@Valid @RequestBody Post post,
                                             BindingResult result, @PathVariable Long id) {

            if (result.hasErrors()) {
                List<String> errors = result.getFieldErrors()
                        .stream()
                        .map(err -> {
                            return "Error en el campo: " + err.getField() + ": " + err.getDefaultMessage();
                        })
                        .collect(Collectors.toList());
                return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
            }

            try {
                postService.updatePost(id, post);
            } catch (DataAccessException e) {
                return new ResponseEntity<>("Error al actualizar", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>("Post actualizado", HttpStatus.CREATED);
        }
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateFields(@RequestBody Map<Object,Object> fields, @PathVariable Long id){
        Post postOld = postService.getPostById(id);
        fields.forEach((key, value)->{
            Field field = ReflectionUtils.findField(Post.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, postOld, value);
        });
        try {
            postService.updatePost(id, postOld);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("Error al actualizar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Post actualizado", HttpStatus.CREATED);
    }



}
