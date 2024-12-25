package com.example.laptopgiahuy2.repository;

import com.example.laptopgiahuy2.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByActiveTrue();
}
