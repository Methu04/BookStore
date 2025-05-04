/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.exception.BookNotFoundException;
import com.example.exception.CartNotFoundException;
import com.example.exception.CustomerNotFoundException;
import com.example.exception.InvalidInputException;
import com.example.exception.OutOfStockException;
import com.example.model.Book;
import com.example.model.Customer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author HP
 */
@Path("customers/{customerId}/orders")
public class OrderResource {
    
    private static Map<Integer, List<Book>> customerCarts = CartResource.customerCarts;
    private static Map<Integer, Map<Integer, List<Book>>> orders = new HashMap<>();
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOrder(@PathParam("customerId") int customerId){
        customerValidity(customerId);
        List<Book> cart = customerCarts.get(customerId);
        
        if(cart==null || cart.isEmpty()){
            throw new CartNotFoundException("Cart is empty!!");
        }
        
        List<Book> newOrder = new ArrayList<>();
        double total = 0;
        for(Book book: cart){
            Book storedBook = null;
            for (Book b : BookResource.books) {
                if (b.getBookId() == book.getBookId()) {
                    storedBook = b;
                    break;
                }
            }
            
            if (book.getStockQuantity() > storedBook.getStockQuantity() || book.getStockQuantity() < 0) {
                throw new OutOfStockException(
                    "Not enough stock for Book ID " + book.getBookId() +
                    ". Requested: " + book.getStockQuantity() +
                    ", Available: " + storedBook.getStockQuantity()
                );
    }

            storedBook.setStockQuantity(storedBook.getStockQuantity()- book.getStockQuantity());

            newOrder.add(book);
            total+= book.getPrice() * book.getStockQuantity();
        }
        int orderId = 1;
        
        if(orders.containsKey(customerId)){
            Map<Integer, List<Book>> customerOrders = orders.get(customerId);
            orderId = customerOrders.size()+1;
            customerOrders.put(orderId, newOrder);
            
        }else{
            Map<Integer, List<Book>> customerOrders = new HashMap<>();
            customerOrders.put(orderId, newOrder);
            orders.put(customerId, customerOrders);
        }
        
        customerCarts.remove(customerId);
        
        
        Response res = Response.status(Response.Status.CREATED).entity("{\"message\":\"Order has been created with the order id: "+ orderId+ " and the total is: " + total+"\"}")
                    .type(MediaType.APPLICATION_JSON).build();
        return res;
        
        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<String, Object>> getOrders(@PathParam("customerId") int customerId){
        customerValidity(customerId);
        Map<Integer,List<Book>> customerOrders = orders.get(customerId);
        
        if(customerOrders == null){
            throw new InvalidInputException("No orders found !!!");
        }
        
        List<Map<String, Object>> orderList = new ArrayList<>();

        for (Integer id : customerOrders.keySet()) {
            List<Book> books = customerOrders.get(id);

            double total = 0;
            for (Book book : books) {
                total += book.getPrice() * book.getStockQuantity();
            }
        
            Map<String, Object> display = new HashMap<>();

            display.put("Order Id", id);
            display.put("Books", books);
            display.put("Total price", total);
            orderList.add(display);
        }
        return orderList;
        
    }
    
    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getOrderById(@PathParam("customerId") int customerId,@PathParam("orderId") int orderId){
        customerValidity(customerId);
        Map<Integer,List<Book>> customerOrders = orders.get(customerId);
        
        if(customerOrders == null){
            throw new InvalidInputException("No orders found !!!");
        }
        List<Book> bookOrder = customerOrders.get(orderId);
        if (bookOrder == null || bookOrder.isEmpty()) {
            throw new InvalidInputException("Order ID " + orderId + " not found for customer ID: " + customerId);
        }
        
        double total = 0;
        for(Book book: bookOrder){
            total += book.getPrice() * book.getStockQuantity();
        }
        
        Map<String, Object> display = new HashMap<>();
        
        display.put("Order Id", orderId);
        display.put("Books", bookOrder);
        display.put("Total price", total);
        return display;
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
