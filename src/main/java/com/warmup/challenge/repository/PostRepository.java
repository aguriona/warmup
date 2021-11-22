package com.warmup.challenge.repository;

import com.warmup.challenge.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT f FROM Post f ORDER BY f.fechaCreacion DESC")
    Optional<List<Post>> findByFecha();
}
