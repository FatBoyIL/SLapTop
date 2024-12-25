package com.example.laptopgiahuy2.service;

import com.example.laptopgiahuy2.model.Comment;

import java.util.List;

public interface CommentService {
    Comment saveComment(Comment comment,Integer productId);
    List<Comment> getComments();
    Boolean updateCommentActicve(Integer id,Boolean isActicve);
    List<Comment> getActicveComments();
}
