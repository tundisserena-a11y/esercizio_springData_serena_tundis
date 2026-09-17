package it.aulab.progetto_blog.dtos;


public class PostDto {

    private Long id;
    private String title;
    private String body;
    private String date;
    private String authorFirstnameAndLastname; // Per mostrare nome e cognome dell'autore insieme

    // costruttore
    public PostDto() {
    }

    // getter e setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthorFirstnameAndLastname() {
        return authorFirstnameAndLastname;
    }

    public void setAuthorFirstnameAndLastname(String authorFirstnameAndLastname) {
        this.authorFirstnameAndLastname = authorFirstnameAndLastname;
    }
}
