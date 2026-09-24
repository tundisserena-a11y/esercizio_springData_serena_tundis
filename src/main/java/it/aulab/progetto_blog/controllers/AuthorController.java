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

import it.aulab.progetto_blog.dtos.AuthorDto;
import it.aulab.progetto_blog.models.Author;
import it.aulab.progetto_blog.services.AuthorService;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private ModelMapper instanceModelMapper;

    @GetMapping
    public String index(Model viewModel) {
        viewModel.addAttribute("title", "Tutti gli autori");
        viewModel.addAttribute("authors", authorService.readAll());
        return "authors";
    }

    @GetMapping("create")
    public String createAuthorView(Model viewModel) {
        viewModel.addAttribute("title", "Aggiungi Autore");
        viewModel.addAttribute("author", new AuthorDto());
        return "createAuthor";
    }

    @PostMapping
    public String createAuthor(@ModelAttribute("author") AuthorDto authorDto) {
        Author author = instanceModelMapper.map(authorDto, Author.class);
        authorService.create(author);
        return "redirect:/authors";
    }

    @GetMapping("edit/{id}")
    public String editAuthorView(@PathVariable("id") Long id, Model viewModel) {
        viewModel.addAttribute("title", "Modifica Autore");
        viewModel.addAttribute("author", authorService.read(id));
        return "editAuthor";
    }

    @PostMapping("update/{id}")
    public String updateAuthor(@PathVariable("id") Long id, @ModelAttribute("author") AuthorDto authorDto) {
        Author author = instanceModelMapper.map(authorDto, Author.class);
        authorService.update(id, author);
        return "redirect:/authors";
    }

    @GetMapping("delete/{id}")
    public String deleteAuthor(@PathVariable("id") Long id) {
        authorService.delete(id);
        return "redirect:/authors";
    }
}
