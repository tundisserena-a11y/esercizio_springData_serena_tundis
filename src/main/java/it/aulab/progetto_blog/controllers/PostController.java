package it.aulab.progetto_blog.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.aulab.progetto_blog.dtos.PostDto;
import it.aulab.progetto_blog.models.Post;
import it.aulab.progetto_blog.services.PostService;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private ModelMapper instanceModelMapper; 

    @GetMapping
    public String index(Model viewModel) {
        viewModel.addAttribute("title", "Tutti i Post");
        viewModel.addAttribute("posts", postService.readAll());
        return "post"; 
    }

    @GetMapping("create")
    public String createPostView(Model viewModel) {
        viewModel.addAttribute("title", "Aggiungi Post");
        viewModel.addAttribute("post", new PostDto());
        return "createPost"; 
    }

    @PostMapping
    public String createPost(@ModelAttribute("post") PostDto postDto) {
        Post post = instanceModelMapper.map(postDto, Post.class);
        postService.create(post);
        return "redirect:/posts";
    }

    @GetMapping("edit/{id}")
    public String editPostView(@PathVariable("id") Long id, Model viewModel) {
        viewModel.addAttribute("title", "Modifica Post");
        viewModel.addAttribute("post", postService.read(id)); 
        return "editPost"; 
    }

    @PostMapping("update/{id}")
    public String updatePost(@PathVariable("id") Long id, @ModelAttribute("post") PostDto postDto) {
        Post post = instanceModelMapper.map(postDto, Post.class);
        postService.update(id, post);
        return "redirect:/posts";
    }

    @GetMapping("delete/{id}")
    public String deletePost(@PathVariable("id") Long id) {
        postService.delete(id);
        return "redirect:/posts";
    }
}
