/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.exception.AuthorNotFoundException;
import com.example.exception.BookNotFoundException;
import com.example.exception.InvalidInputException;
import com.example.model.Author;
import com.example.model.Book;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author HP
 */
@Path("/authors")
public class AuthResource {
    private static List<Author> authors = new ArrayList<>();
    
    static{
        authors.add(new Author(1,"Herman Melville","Herman Melville was an American novelist, short story writer, and poet of the American Renaissance period. Among his best-known works are Moby-Dick"));
        authors.add(new Author(2,"Charles Dickens","Charles John Huffam Dickens was an English novelist, journalist, short story writer and social critic. He created some of literature's best-known fictional characters, and is regarded by many as the greatest novelist of the Victorian era."));
        authors.add(new Author(3,"Charlotte Brontë","Charlotte Nicholls, commonly known as Charlotte Brontë, was an English novelist and poet, the eldest of the three Brontë sisters who survived into adulthood and whose novels became classics of English literature."));
        authors.add(new Author(4,"Emily Brontë","Emily Jane Brontë was an English novelist and poet who is best known for her only novel, Wuthering Heights, now considered a classic of English literature."));
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Author> getAllAuthors(){
        return authors;
    }
    
    @GET
    @Path("/{authorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Author getAuthorById(@PathParam ("authorId") int authId){
        for(Author author:authors){
            if(author.getAuthId() == authId){
                return author;
            }
        }
        throw new AuthorNotFoundException("Author with ID " + authId + " not found");
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAuthor(Author author){
        int authId = author.getAuthId();
        if (authId <= 0) {
            throw new InvalidInputException("Enter an ID that is greater than 0 !!!");
        }
        for (Author a : authors) {
            if (a.getAuthId() == authId) {
                throw new InvalidInputException("Author ID " + authId + " already exists!");
            }
        }
        if(author.getBiography()==null || author.getBiography().trim().isEmpty()){
            throw new InvalidInputException("Enter the biography !!!");
        }
        if(author.getName()==null || author.getName().trim().isEmpty()){
            throw new InvalidInputException("Enter the name !!!");
        }
        
        authors.add(author);
        
        Response res = Response.status(Response.Status.CREATED).entity("{\"message\":\"Author saved successfully!!!\"}")
                .type(MediaType.APPLICATION_JSON).build();
        return res;
        
    }
    
    @PUT
    @Path("/{authorId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAuthor(@PathParam ("authorId") int authId,Author updatedAuthor){
        for(int i = 0; i<authors.size();i++){
            if(authors.get(i).getAuthId() == authId){
                if(updatedAuthor.getBiography()==null || updatedAuthor.getBiography().trim().isEmpty()){
                    throw new InvalidInputException("Enter the biography !!!");
                }
                if(updatedAuthor.getName()==null || updatedAuthor.getName().trim().isEmpty()){
                    throw new InvalidInputException("Enter the name !!!");
                }
                updatedAuthor.setAuthId(authId);
                authors.set(i, updatedAuthor);
                
                
                Response res = Response.status(Response.Status.CREATED).entity("{\"message\":\"Author updated successfully!!!\"}")
                    .type(MediaType.APPLICATION_JSON).build();
                return res;
                
            }  
        }
        throw new AuthorNotFoundException("Author with ID " + authId + " not found for update");
    }
    
    @DELETE
    @Path("/{authorId}")
    public Response deleteAuthor(@PathParam ("authorId") int authId){
        boolean removed = authors.removeIf(author -> author.getAuthId() == authId);
        if(!removed){
            throw new AuthorNotFoundException("Author with ID " + authId + " not found for deletion"); 
            
        }
        
        Response res = Response.status(Response.Status.OK).entity("{\"message\":\"Author deleted successfully!!!\"}")
                    .type(MediaType.APPLICATION_JSON).build();
        return res;
        
    }
    
    
    @GET
    @Path("/{authorId}/books")
    @Produces(MediaType.APPLICATION_JSON)
    public String getBookforAuthor(@PathParam ("authorId") int authId){
        for(Author author:authors){
            if(author.getAuthId() == authId){
                if(author != null){
                    List<Book> selectedBooks = new ArrayList<>();
                    for(Book book: BookResource.getAllBooks()){
                        if(book.getAuthId()==authId){
                            selectedBooks.add(book);
                        }
                    }
                    if(!selectedBooks.isEmpty()){
                        String json = "[";
                        for(int i = 0;i<selectedBooks.size();i++){
                            Book selectedBook = selectedBooks.get(i);
                            json += "{" + "\"book\": \""+ selectedBook.getTitle()+
                                "\"," + "\"author\": \"" + selectedBook.getAuthId()+
                                "\"" + "}";
                            if(1!= selectedBooks.size()-1){
                                json+=",";
                            }
                        }
                        json+="]";
                        return json;
                    }else{
                        throw new BookNotFoundException("Book with authID " + authId + " not found.");
                    }
                }
            }
        }
        throw new AuthorNotFoundException("Author with ID " + authId + " not found"); 
        
    }
}
