/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.exception.CustomerNotFoundException;
import com.example.exception.InvalidInputException;
import com.example.model.Customer;
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
@Path("/customers")
public class CustomerResource {
    public static List<Customer> customers = new ArrayList<>();
    
    static{
        customers.add(new Customer(1,"John Doe","john@gmail.com","John123"));
        customers.add(new Customer(2,"Jane Smith","jane@gmail.com","Jane123"));
        customers.add(new Customer(3,"Bob Lee","bob@gmail.com","Bob123"));
        customers.add(new Customer(4,"Tom Ray","tom@gmail.com","Tom123"));
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getAllCustomers(){
        return customers;
    }
    
    @GET
    @Path("/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer getCustomerById(@PathParam ("customerId") int customerId){
        return customers.stream().filter(customer -> customer.getCustomerId()==customerId).findFirst().orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + customerId + " not found"));
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCustomer(Customer customer){
        int newCustomerId = getNextCustomerId();
        customer.setCustomerId(newCustomerId);
        if(customer.getEmail()==null || customer.getEmail().trim().isEmpty()){
            throw new InvalidInputException("Enter the email !!!");
        }
        if(customer.getName()==null || customer.getName().trim().isEmpty()){
            throw new InvalidInputException("Enter the name !!!");
        }
        if(customer.getPassword()==null || customer.getPassword().trim().isEmpty()){
            throw new InvalidInputException("Enter the password !!!");
        }
        customers.add(customer);
        
        
        Response res = Response.status(Response.Status.CREATED).entity("{\"message\":\"Customer saved!!!\"}")
                    .type(MediaType.APPLICATION_JSON).build();
        return res;
        
    }
    
    public int getNextCustomerId(){
        int maxUserId = Integer.MIN_VALUE;
        
        for(Customer customer: customers){
            int userId = customer.getCustomerId();
            if(userId>maxUserId){
                maxUserId = userId;
            }
        }
        return maxUserId+1;
    }
    
    @PUT
    @Path("/{customerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam ("customerId") int customerId, Customer updatedCustomer){
        for(int i = 0; i<customers.size();i++){
            if(customers.get(i).getCustomerId() == customerId){
                if(updatedCustomer.getEmail()==null || updatedCustomer.getEmail().trim().isEmpty()){
                    throw new InvalidInputException("Enter the email !!!");
                }
                if(updatedCustomer.getName()==null || updatedCustomer.getName().trim().isEmpty()){
                    throw new InvalidInputException("Enter the name !!!");
                }
                if(updatedCustomer.getPassword()==null || updatedCustomer.getPassword().trim().isEmpty()){
                    throw new InvalidInputException("Enter the password !!!");
                }
                updatedCustomer.setCustomerId(customerId);
                customers.set(i, updatedCustomer);
                
                
                Response res = Response.status(Response.Status.CREATED).entity("{\"message\":\"Customer Updated!!!\"}")
                    .type(MediaType.APPLICATION_JSON).build();
                return res;
            } 
        }
        throw new CustomerNotFoundException("Customer with ID " + customerId + " not found for update");
        
    }
    
    @DELETE
    @Path("/{customerId}")
    public Response deleteCustomer(@PathParam ("customerId") int customerId){
        boolean removed = customers.removeIf(customer -> customer.getCustomerId() == customerId);
        if(!removed){
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found for deletion"); 
            
        }
        
        
        Response res = Response.status(Response.Status.OK).entity("{\"message\":\"Customer deleted!!!\"}")
                    .type(MediaType.APPLICATION_JSON).build();
        return res;
        
        
    }
    
}
