package it.aulab.progetto_blog.repositories;

import org.springframework.data.repository.CrudRepository;

import it.aulab.progetto_blog.models.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long>{

}
