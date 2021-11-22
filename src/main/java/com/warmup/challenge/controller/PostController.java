package com.warmup.challenge.controller;

import com.warmup.challenge.entity.Post;
import com.warmup.challenge.entity.PostDto;
import com.warmup.challenge.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<?> savePost(@RequestBody Post post){
//        if (result.hasErrors()){
//            return new ResponseEntity<>(result.getGlobalError(), HttpStatus.BAD_REQUEST);
//        } else{
            return new ResponseEntity<Post>(postService.createPost(post),HttpStatus.CREATED);
      //  }


    }

}
