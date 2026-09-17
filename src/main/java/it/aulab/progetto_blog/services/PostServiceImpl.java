package it.aulab.progetto_blog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.aulab.progetto_blog.dtos.PostDto;
import it.aulab.progetto_blog.models.Post;
import it.aulab.progetto_blog.models.Comment;
import it.aulab.progetto_blog.repositories.PostRepository;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<PostDto> readAll() {
        // lista che conterra tutti gli oggetti post convertiti in DTO
        List<PostDto> dtos = new ArrayList<PostDto>();
        for (Post post : postRepository.findAll()) {
            dtos.add(mapper.map(post, PostDto.class));
        }
        return dtos;
    }

    @Override
    public PostDto read(Long id) {
        // per cercare il post in modo sicuro tramite ID
        Optional<Post> optPost = postRepository.findById(id);
        if (optPost.isPresent()) {
            return mapper.map(optPost.get(), PostDto.class);
        } else {
            // Se non lo trovo lancio l'eccezione 404 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post id=" + id + " not found");
        }
    }

    @Override
    public PostDto create(Post post) {
        //  se mancano titolo o corpo blocco la richiesta
        if (post.getTitle() == null || post.getBody() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return mapper.map(postRepository.save(post), PostDto.class);
    }

    @Override
    public PostDto update(Long id, Post post) {
        // Verifico se il post esiste prima di aggiornarlo
        if (postRepository.existsById(id)) {
            post.setId(id);
            return mapper.map(postRepository.save(post), PostDto.class);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void delete(Long id) {
        // Prima di cancellare il post, scollego tutti i commenti ad esso associati
        if (postRepository.existsById(id)) {
            Post post = postRepository.findById(id).get();
            List<Comment> postComments = post.getComments();
            for (Comment comment : postComments) {
                comment.setPost(null);
            }
            postRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
    }
}
