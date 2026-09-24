package it.aulab.progetto_blog.controllers;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.aulab.progetto_blog.dtos.CommentDto;
import it.aulab.progetto_blog.models.Comment;
import it.aulab.progetto_blog.models.Post;
import it.aulab.progetto_blog.repositories.PostRepository;
import it.aulab.progetto_blog.services.CommentService;

@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ModelMapper instanceModelMapper; 

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public String index(Model viewModel) {
        viewModel.addAttribute("title", "Tutti i Commenti");
        viewModel.addAttribute("comments", commentService.readAll());
        return "comment"; 
    }

    @GetMapping("create")
    public String createCommentView(Model viewModel) {
        viewModel.addAttribute("title", "Aggiungi Commento");
        CommentDto comment = new CommentDto();
        comment.setDate(LocalDate.now().toString());
        viewModel.addAttribute("comment", comment);
        viewModel.addAttribute("posts", postRepository.findAll());
        return "createComment";
    }

    @PostMapping
    public String createComment(@ModelAttribute("comment") CommentDto commentDto) {
        Comment comment = instanceModelMapper.map(commentDto, Comment.class);
        comment.setPost(findPost(commentDto.getPostId()));
        commentService.create(comment);
        return "redirect:/comments";
    }

    @GetMapping("edit/{id}")
    public String editCommentView(@PathVariable("id") Long id, Model viewModel) {
        viewModel.addAttribute("title", "Modifica Commento");
        viewModel.addAttribute("comment", commentService.read(id));
        viewModel.addAttribute("posts", postRepository.findAll());
        return "editComment"; 
    }

    @PostMapping("update/{id}")
    public String updateComment(@PathVariable("id") Long id, @ModelAttribute("comment") CommentDto commentDto) {
        Comment comment = instanceModelMapper.map(commentDto, Comment.class);
        comment.setPost(findPost(commentDto.getPostId()));
        commentService.update(id, comment);
        return "redirect:/comments";
    }

    @GetMapping("delete/{id}")
    public String deleteComment(@PathVariable("id") Long id) {
        commentService.delete(id);
        return "redirect:/comments";
    }

    private Post findPost(Long postId) {
        if (postId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il post è obbligatorio");
        }
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Post non trovato: " + postId));
    }
}
