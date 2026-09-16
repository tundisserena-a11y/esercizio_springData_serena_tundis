package it.aulab.progetto_blog.repositories;

import org.springframework.data.repository.CrudRepository;

import it.aulab.progetto_blog.models.Post;

public interface PostRepository extends CrudRepository<Post, Long> {

}
