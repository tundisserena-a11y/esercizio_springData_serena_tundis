package it.aulab.progetto_blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.aulab.progetto_blog.models.Author;
import it.aulab.progetto_blog.repositories.AuthorRepository;

@Controller
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;
    
    //metodo API
    @RequestMapping(value="/authors", method=RequestMethod.GET)
    public @ResponseBody List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

}
