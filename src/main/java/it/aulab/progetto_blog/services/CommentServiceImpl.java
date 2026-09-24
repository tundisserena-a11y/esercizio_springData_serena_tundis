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
            dtos.add(toDto(comment));
        }
        return dtos;
    }

    @Override
    public CommentDto read(Long id) {
        // Cerco il commento tramite ID 
        Optional<Comment> optComment = commentRepository.findById(id);
        if (optComment.isPresent()) {
            return toDto(optComment.get());
        } else {
            // Se non lo trovo lancio la classica eccezione 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment id=" + id + " not found");
        }
    }

    @Override
    public CommentDto create(Comment comment) {
        if (comment.getEmail() == null || comment.getBody() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return toDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto update(Long id, Comment comment) {
        // Controllo se il commento esiste prima di modificarlo
        if (commentRepository.existsById(id)) {
            comment.setId(id);
            return toDto(commentRepository.save(comment));
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

    private CommentDto toDto(Comment comment) {
        CommentDto dto = mapper.map(comment, CommentDto.class);
        if (comment.getPost() != null) {
            dto.setPostId(comment.getPost().getId());
        }
        return dto;
    }
}
