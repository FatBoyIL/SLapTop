package com.example.laptopgiahuy2.service.Implement;

import com.example.laptopgiahuy2.model.Comment;
import com.example.laptopgiahuy2.model.Product;
import com.example.laptopgiahuy2.model.UserDtls;
import com.example.laptopgiahuy2.repository.CommentRepository;
import com.example.laptopgiahuy2.repository.ProductRepository;
import com.example.laptopgiahuy2.repository.UserDtlsRepository;
import com.example.laptopgiahuy2.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private ProductRepository productRepository;

    public CommentServiceImpl(CommentRepository commentRepository, ProductRepository productRepository, UserDtlsRepository userDtlsRepository) {
        this.commentRepository = commentRepository;
        this.productRepository = productRepository;
    }


    @Override
    public Comment saveComment(Comment comment,Integer productId) {
        Product product = productRepository.findById(productId).orElse(null);
        comment.setProduct(product);
        comment.setActive(false);
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }
    public Boolean updateCommentActicve(Integer id, Boolean status) {
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isPresent()) {
            Comment comment1 = comment.get();
            comment1.setActive(status);
            commentRepository.save(comment1);
            return true;
        }
        return false;
    }

    @Override
    public List<Comment> getActicveComments() {
        return commentRepository.findByActiveTrue();
    }
}
