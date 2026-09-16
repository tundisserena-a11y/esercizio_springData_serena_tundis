package it.aulab.progetto_blog.repositories;

import org.springframework.data.repository.ListCrudRepository;

import it.aulab.progetto_blog.models.Post;

public interface PostRepository extends ListCrudRepository<Post, Long> {

}
