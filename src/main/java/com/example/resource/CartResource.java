/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;


import com.example.exception.BookNotFoundException;
import com.example.exception.CustomerNotFoundException;
import com.example.exception.InvalidInputException;
import com.example.exception.OutOfStockException;
import com.example.model.Book;
import com.example.model.Customer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@Path("/customers/{customerId}/cart")
public class CartResource {
    public static Map<Integer, List<Book>> customerCarts = new HashMap<>();
    

    @POST
    @Path("/items")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addToCart(@PathParam("customerId") int customerId, Book book) {
        customerValidity(customerId);
        Book storedBook = null;
        for (Book b : BookResource.books) {
            if (b.getBookId() == book.getBookId()) {
                storedBook = b;
                break;
            }
        }
        if (storedBook == null) {
            throw new BookNotFoundException("Book with ID " + book.getBookId() + " not found in store.");
        }
        
        if (book.getStockQuantity()> storedBook.getStockQuantity() || book.getStockQuantity()<0) {
                throw new OutOfStockException(
                    "Not enough stock for Book ID " + book.getBookId() +
                    ". Requested: " + book.getStockQuantity()+
                    ", Available: " + storedBook.getStockQuantity()
                );
            }
        if (book.getYear() != storedBook.getYear()) {
            throw new InvalidInputException("Year does not match the existing Book with ID " + book.getBookId());
        }
        if (!book.getISBN().equals(storedBook.getISBN())) {
            throw new InvalidInputException("ISBN does not match the existing Book with ID " + book.getBookId());
        }
        if (book.getPrice()!= storedBook.getPrice()) {
            throw new InvalidInputException("Price does not match the existing Book with ID " + book.getBookId());
        }
        if (book.getAuthId()!= storedBook.getAuthId()) {
            throw new InvalidInputException("Author id does not match the existing Book with ID " + book.getBookId());
        }
        if (!(book.getTitle().equalsIgnoreCase(storedBook.getTitle()))) {
            throw new InvalidInputException("Title does not match the existing Book with ID " + book.getBookId());
        }
        
    
        List<Book> cart;
        if(customerCarts.containsKey(customerId)){
            cart = customerCarts.get(customerId);
        }else{
            cart = new ArrayList<>();
        }
        boolean found = false;
        for(Book b:cart){
            if(b.getBookId()==book.getBookId()){
                b.setStockQuantity(b.getStockQuantity()+book.getStockQuantity());
                found = true;
                break;
            }
        }
        if (!found){
            cart.add(book);
        }
        
        customerCarts.put(customerId, cart);

        
        Response res = Response.status(Response.Status.CREATED).entity("{\"message\":\"Book added to customer " + customerId + "'s cart.\"}")
                    .type(MediaType.APPLICATION_JSON).build();
        return res;
        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getCart(@PathParam("customerId") int customerId) {
        customerValidity(customerId);
        if(customerCarts.containsKey(customerId)){
            return customerCarts.get(customerId);
        }else{
            return new ArrayList<>();
            
        }
        
        
    }
    
    @PUT
    @Path("/items/{bookId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCart(@PathParam ("bookId") int bookId , Book updatebook, @PathParam("customerId") int customerId){
        customerValidity(customerId);
        Book storedBook = null;
        for (Book b : BookResource.books) {
            if (b.getBookId() == updatebook.getBookId()) {
                storedBook = b;
                break;
            }
        }
        if (storedBook == null) {
            throw new BookNotFoundException("Book with ID " + updatebook.getBookId() + " not found in store.");
        }
        
        if (updatebook.getStockQuantity()> storedBook.getStockQuantity() || updatebook.getStockQuantity()<0) {
                throw new OutOfStockException(
                    "Not enough stock for Book ID " + updatebook.getBookId() +
                    ". Requested: " + updatebook.getStockQuantity()+
                    ", Available: " + storedBook.getStockQuantity()
                );
        }
        if (updatebook.getYear() != storedBook.getYear()) {
            throw new InvalidInputException("Year does not match the existing Book with ID " + updatebook.getBookId());
        }
        if (updatebook.getPrice()!= storedBook.getPrice()) {
            throw new InvalidInputException("Price does not match the existing Book with ID " + updatebook.getBookId());
        }
        if (!updatebook.getISBN().equals(storedBook.getISBN())) {
            throw new InvalidInputException("ISBN does not match the existing Book with ID " + updatebook.getBookId());
        }
        if (updatebook.getAuthId()!= storedBook.getAuthId()) {
            throw new InvalidInputException("Author id does not match the existing Book with ID " + updatebook.getBookId());
        }
        if (!(updatebook.getTitle().equalsIgnoreCase(storedBook.getTitle()))) {
            throw new InvalidInputException("Title does not match the existing Book with ID " + updatebook.getBookId());
        }
        List<Book> cart = customerCarts.get(customerId);
        
        if (cart == null) {
            throw new InvalidInputException("No cart found for customer ID " + customerId);
        }
        
        for(int i = 0; i<cart.size();i++){
            if(cart.get(i).getBookId() == bookId){
                updatebook.setBookId(bookId);
                cart.set(i, updatebook);
                
                Response res = Response.status(Response.Status.CREATED).entity("{{\"message\":\"Cart updated!!!\"}")
                    .type(MediaType.APPLICATION_JSON).build();
                return res;
                
                
            }  
        }
        throw new BookNotFoundException("Book with ID " + bookId + " not found in store.");
    }
    
    @DELETE
    @Path("/items/{bookId}")
    public Response deleteCart(@PathParam ("bookId") int bookId, @PathParam("customerId") int customerId){
        customerValidity(customerId);
        List<Book> cart = customerCarts.get(customerId);
        boolean removed = cart.removeIf(book -> book.getBookId() == bookId);
        if(!removed){
            throw new BookNotFoundException("Book with ID " + bookId + " not found for deletion"); 
        }
        
        Response res = Response.status(Response.Status.OK).entity("{\"message\":\"Cart deleted!!!\"}")
                    .type(MediaType.APPLICATION_JSON).build();
        return res;
         
    }
    
    public static void customerValidity(int customerId){
        boolean found = false;
        for (Customer customer : CustomerResource.customers) {
            if (customer.getCustomerId() == customerId) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
        }
    }
    

}
