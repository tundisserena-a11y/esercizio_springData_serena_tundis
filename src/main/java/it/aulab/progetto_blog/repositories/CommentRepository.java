package it.aulab.progetto_blog.repositories;

import org.springframework.data.repository.ListCrudRepository;

import it.aulab.progetto_blog.models.Comment;

public interface CommentRepository extends ListCrudRepository<Comment, Long>{

}
