/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.exception;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author HP
 */
@Provider
public class CustomerNotFoundExceptionMapper implements ExceptionMapper<CustomerNotFoundException>{
    
    
    @Override
    public Response toResponse(CustomerNotFoundException exception){
        
        
        Response res = Response.status(Response.Status.NOT_FOUND).entity("{\"error\": \""+exception.getMessage()+"\"}")
                .type(MediaType.APPLICATION_JSON).build();
        return res;
        
    }
}
