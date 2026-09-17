package it.aulab.progetto_blog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.aulab.progetto_blog.dtos.CommentDto;
import it.aulab.progetto_blog.models.Comment;
import it.aulab.progetto_blog.repositories.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<CommentDto> readAll() {
        // lista che conterra tutti gli oggetti comment convertiti in DTO
        List<CommentDto> dtos = new ArrayList<CommentDto>();
        for (Comment comment : commentRepository.findAll()) {
            dtos.add(mapper.map(comment, CommentDto.class));
        }
        return dtos;
    }

    @Override
    public CommentDto read(Long id) {
        // Cerco il commento tramite ID 
        Optional<Comment> optComment = commentRepository.findById(id);
        if (optComment.isPresent()) {
            return mapper.map(optComment.get(), CommentDto.class);
        } else {
            // Se non lo trovo lancio la classica eccezione 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment id=" + id + " not found");
        }
    }

    @Override
    public CommentDto create(Comment comment) {
        if (comment.getEmail() == null || comment.getBody() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return mapper.map(commentRepository.save(comment), CommentDto.class);
    }

    @Override
    public CommentDto update(Long id, Comment comment) {
        // Controllo se il commento esiste prima di modificarlo
        if (commentRepository.existsById(id)) {
            comment.setId(id);
            return mapper.map(commentRepository.save(comment), CommentDto.class);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void delete(Long id) {
        // Cancello il commento solo se esiste nel database
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
    }
}
