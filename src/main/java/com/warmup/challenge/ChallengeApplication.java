package com.warmup.challenge;

import com.github.javafaker.Faker;
import com.warmup.challenge.entity.Post;
import com.warmup.challenge.entity.Usuario;
import com.warmup.challenge.repository.PostRepository;
import com.warmup.challenge.service.PostService;
import com.warmup.challenge.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeApplication implements ApplicationRunner {
	@Autowired
	private Faker faker;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private PostService postService;
	@Autowired
	private PostRepository postRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		for (int i=0; i<5; i++) {
			Usuario user = new Usuario();
			user.setEmail(faker.internet().emailAddress());
			user.setPassword(faker.internet().password());
			usuarioService.createUser(user);
		}
		for (int i=0; i<20; i++) {
			Post post = new Post();
			post.setTitulo(faker.book().title());
			post.setContenido(faker.hobbit().location());
			post.setFechaCreacion(faker.date().birthday());
			post.setImagen(faker.internet().image());
			post.setCategoria(faker.color().name());
			postRepository.save(post);
		}
	}
}
