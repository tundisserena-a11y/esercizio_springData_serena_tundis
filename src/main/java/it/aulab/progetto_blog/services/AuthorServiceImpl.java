package it.aulab.progetto_blog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.aulab.progetto_blog.dtos.AuthorDto;
import it.aulab.progetto_blog.models.Author;
import it.aulab.progetto_blog.models.Post;
import it.aulab.progetto_blog.repositories.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<AuthorDto> readAll() {
        List<AuthorDto> dtos = new ArrayList<AuthorDto>(); // lista che conterra tutti gli oggetti author convertiti
        for (Author author : authorRepository.findAll()) {
            dtos.add(mapper.map(author, AuthorDto.class));
        }
        return dtos;

    }

    @Override
    public AuthorDto read(Long id) {
        Optional<Author> optAuthor = authorRepository.findById(id);
        if (optAuthor.isPresent()) {
            return mapper.map(optAuthor.get(), AuthorDto.class);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author id=" + id + " not found");
        }
    }

    @Override
    public List<AuthorDto> read(String email) {
        if (email == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        List<AuthorDto> dtos = new ArrayList<AuthorDto>();
        for (Author author : authorRepository.findByEmail(email)) {
            dtos.add(mapper.map(author, AuthorDto.class));
        }
        return dtos;
    }

    @Override
    public List<AuthorDto> read(String firstname, String lastname) {
        if (firstname == null || lastname == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        List<AuthorDto> dtos = new ArrayList<AuthorDto>();
        for (Author author : authorRepository.findByNameAndSurname(firstname, lastname)) {
            dtos.add(mapper.map(author, AuthorDto.class));
        }
        return dtos;
    }

    @Override
    public AuthorDto create(Author author) {
        // Validation
        if (author.getEmail() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return mapper.map(authorRepository.save(author), AuthorDto.class);
    }

    @Override
    public AuthorDto update(Long id, Author author) {
        if (authorRepository.existsById(id)) {
            author.setId(id);
            return mapper.map(authorRepository.save(author), AuthorDto.class);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void delete(Long id) {
        if (authorRepository.existsById(id)) {
            Author author = authorRepository.findById(id).get();
            List<Post> authorPosts = author.getPosts();
            for (Post post : authorPosts) {
                post.setAuthor(null);
            }
            authorRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
        }
    }

}
