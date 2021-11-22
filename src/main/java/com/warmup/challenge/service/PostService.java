package com.warmup.challenge.service;

import com.warmup.challenge.entity.Post;
import com.warmup.challenge.entity.PostDto;
import com.warmup.challenge.repository.PostRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;


    public Post createPost(Post post) {
         return postRepository.save(post);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Id %d no encontrado", id)));
    }

    public List<PostDto> getPostsByFechaCreacion() {
        //Otra forma
        // return postRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaCreacion"));
        List<Post> postsByFechaList = postRepository.findByFecha()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Posts no encontrados"));

        List<PostDto> postsByFechaListDto = new ArrayList<>();

        for (Post posts : postsByFechaList) {
            PostDto postDto = new PostDto();
            postDto.setTitulo(posts.getTitulo());
            postDto.setId(posts.getId());
            postDto.setImagen(posts.getImagen());
            postDto.setFechaCreacion(posts.getFechaCreacion());
            postDto.setCategoria(posts.getCategoria());
            postsByFechaListDto.add(postDto);
        }

        return postsByFechaListDto;

    }

    public List<PostDto> filtrarTitulo(String titulo) {

        List<PostDto> postDtoList = getPostsByFechaCreacion();

        return postDtoList.stream().filter(postDto -> postDto.getTitulo()
                                    .equals(titulo))
                                    .collect(Collectors.toList());

    }

    public List<PostDto> filtrarCategoria(String categoria) {

        List<PostDto> postDtoList = getPostsByFechaCreacion();

        return postDtoList.stream()
                        .filter(postDto -> postDto.getCategoria()
                        .equals(categoria))
                        .collect(Collectors.toList());

    }

    public List<Post> listPosts() {
        return postRepository.findAll();
    }

    public void deltePost(Long id) {
        if (postRepository.findById(id).isPresent()) {
            postRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Id %d no encontrado", id));
        }
    }

    public Post updatePost(Post post) {
        return postRepository.save(post);
    }
}
