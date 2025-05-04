/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.exception.BookNotFoundException;
import com.example.exception.InvalidInputException;
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
@Path("/books")
public class BookResource {
    public static List<Book> books = new ArrayList<>();
    private static int currentYear = java.time.Year.now().getValue();
    
    static{
        
        books.add(new Book(1,"Moby-Dick",1,"3453dfdfg",1851,2000.00,50));
        books.add(new Book(2,"Great Expectations",2,"3673dfdfg",1861,3000.00,60));
        books.add(new Book(3,"Jane Eyre",3,"3343dfdfg",1847,2500.00,10));
        books.add(new Book(4,"Wuthering Heights",4,"6787dfdfg",1847,3500.00,80));
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public static List<Book> getAllBooks(){
        return books;
    }
    
    @GET
    @Path("/{bookid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getBookById(@PathParam ("bookid")int bookid){
        for(Book m:books){
            if(m.getBookId() == bookid){
                return m;
            }
        }
        throw new BookNotFoundException("Book with ID " + bookid + " not found");
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(Book book){
        int bookId = book.getBookId();
        if (bookId <= 0) {
            throw new InvalidInputException("Enter an ID that is greater than 0 !!!");
        }
        for (Book b : books) {
            if (b.getBookId()== bookId) {
                throw new InvalidInputException("Book ID " + bookId + " already exists!");
            }
        }
        if(book.getISBN()==null || book.getISBN().trim().isEmpty()){
            throw new InvalidInputException("Enter the ISBN !!!");
        }
        if(book.getTitle()==null || book.getTitle().trim().isEmpty()){
            throw new InvalidInputException("Enter the title !!!");
        }
        if(book.getPrice()<=0){
            throw new InvalidInputException("Enter a price that is greater than 0 !!!");
        }
        if(book.getStockQuantity()<0){
            throw new InvalidInputException("Enter a quantity that is greater than 0 !!!");
        }
        
        if(book.getYear()> currentYear){
            throw new InvalidInputException("Do not enter a year in the future !!!");
        }
        if (book.getYear() < 1000) {
            throw new InvalidInputException("Publication year seems invalid.");
        }
        
        books.add(book);
        
        Response res = Response.status(Response.Status.CREATED).entity("{\"message\":\"Book saved successfully!!!\"}")
                    .type(MediaType.APPLICATION_JSON).build();
        return res;
        
    }
    
    @PUT
    @Path("/{bookid}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam ("bookid") int bookid , Book updateBook){
        for(int i = 0; i<books.size();i++){
            if(books.get(i).getBookId() == bookid){
                
                if(updateBook.getISBN()==null || updateBook.getISBN().trim().isEmpty()){
                    throw new InvalidInputException("Enter the ISBN !!!");
                }
                if(updateBook.getTitle()==null || updateBook.getTitle().trim().isEmpty()){
                    throw new InvalidInputException("Enter the title !!!");
                }
                if(updateBook.getPrice()<=0){
                    throw new InvalidInputException("Enter a price that is greater than 0 !!!");
                }
                if(updateBook.getStockQuantity()<0){
                    throw new InvalidInputException("Enter a quantity that is greater than 0 !!!");
                }
                if(updateBook.getYear()> currentYear){
                    throw new InvalidInputException("Do not enter a year in the future !!!");
                }
                if (updateBook.getYear() < 1000) {
                    throw new InvalidInputException("Publication year seems invalid.");
                }
                updateBook.setBookId(bookid);
                books.set(i, updateBook);
                System.out.println("Book updated!!! "+updateBook);
               
                Response res = Response.status(Response.Status.CREATED).entity("{\"message\":\"Book updated successfully!!!\"}")
                    .type(MediaType.APPLICATION_JSON).build();
                return res;
                
            }  
        }
        throw new BookNotFoundException("Book with ID " + bookid + " not found for update");
    }
    
    @DELETE
    @Path("/{bookid}")
    public Response deleteBook(@PathParam ("bookid") int bookid){
        boolean removed = books.removeIf(book -> book.getBookId() == bookid);
        if(!removed){
            throw new BookNotFoundException("Book with ID " + bookid + " not found for deletion"); 
            
        }
        
        Response res = Response.status(Response.Status.OK).entity("{\"message\":\"Book deleted successfully !!!\"}")
                    .type(MediaType.APPLICATION_JSON).build();
        return res;
        
    }
}
